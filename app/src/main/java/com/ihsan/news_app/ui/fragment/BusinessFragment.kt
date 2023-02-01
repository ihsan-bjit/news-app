package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentBusinessBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.utils.Utils
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import kotlinx.coroutines.launch

class BusinessFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: NewsviewViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding: FragmentBusinessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("news", "onCreateView: Business")
        binding = FragmentBusinessBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("news", "onViewCreated: Business")
        refreshLayout = binding.swipeLayout
        viewModel = ViewModelProvider(this)[NewsviewViewModel::class.java]

        recyclerView = binding.recyclerviewBusiness
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.getBusinessNewsLocal().observe(viewLifecycleOwner) {
            Log.d("newsBusiness", "onViewCreated Business newsList: ${it.size}")
            val adapterViewState = recyclerView.layoutManager?.onSaveInstanceState()
            recyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            recyclerView.adapter = ArticleAdapter(it)
            if (it.isEmpty()) {
                viewModel.getAllNewsApi()
                Log.d("newsBusiness", "onViewCreated with empty roomData: APi Call ")
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.getAllNewsLocal().observe(viewLifecycleOwner) {
                viewModel.viewModelScope.launch {
                    viewModel.getAllNewsApi()
                    Utils().refreshMessage()
                }
            }
            Log.d("newsBusiness", "onViewCreated: swipe to refresh")
            refreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("news", "onCreateOptionsMenu: business")
        menu.clear()
        inflater.inflate(R.menu.top_search, menu)
        val item = menu.findItem(R.id.topSearchAction)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("news", "onQueryTextChange Business: $newText")
                if (!newText.isNullOrEmpty() && binding.recyclerviewBusiness.adapter!=null) {
                     val adapter = binding.recyclerviewBusiness.adapter as ArticleAdapter
                    adapter.filter(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}