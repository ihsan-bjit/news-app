package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentHomeBinding
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.utils.MyApplication
import com.ihsan.news_app.utils.Utils
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import kotlinx.coroutines.launch

private lateinit var viewModel: NewsviewViewModel

class HomeFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewsviewViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = binding.swipeLayout
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
        recyclerView.setHasFixedSize(true)

//        viewModel = ViewModelProvider(this)[NewsviewViewModel::class.java]
        viewModel.getAllNewsLocal.observe(requireActivity()) {
            Log.d("newsHome", "onViewCreated home newsList: ${it.size}")
            val adapterViewState = recyclerView.layoutManager?.onSaveInstanceState()
            recyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            recyclerView.adapter = ArticleAdapter(it)
            if (it.isEmpty()) {
                Log.d("newsHome", "onViewCreated with empty roomData: APi Call ")
                viewModel.getAllNewsApi()
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.getAllNewsLocal().observe(viewLifecycleOwner) {
                viewModel.viewModelScope.launch {
                    viewModel.getAllNewsApi()
                    Utils().refreshMessage()
                }
            }
            Log.d("newsHome", "onViewCreated: swipe to refresh")
            refreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.top_search, menu)
        val item = menu.findItem(R.id.topSearchAction)
        searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty() && recyclerView.adapter!=null) {
                    val adapter = recyclerView.adapter as ArticleAdapter
                    adapter.filter(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onPause() {
        super.onPause()
        searchView.setQuery("", false)
        Log.d("news", "onPause: home")
    }
}

fun autoReload() {
    viewModel.getAllNewsApi()
}
