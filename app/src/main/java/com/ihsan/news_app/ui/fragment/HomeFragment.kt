package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentHomeBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    val viewModel: NewsviewViewModel by viewModels()
    private lateinit var recyclerView:RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment
//        viewModel = ViewModelProvider(requireActivity())[NewsviewViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.swipeLayout)

        viewModel.getAllNewsLocal().observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                newsList=it
                Log.d("newsHome", "onViewCreated home newsList: ${newsList.size}")
                recyclerView=view.findViewById(R.id.recyclerview)
                recyclerView.layoutManager=LinearLayoutManager(requireContext())
                recyclerView.adapter=ArticleAdapter(requireContext(),viewModel,newsList as ArrayList<NewsTable>)
            }
            else{
                Log.d("newsHome", "onViewCreated else roomData: empty")
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.getAllNewsLocal().observe(viewLifecycleOwner) {
                viewModel.viewModelScope.launch {
                    viewModel.getAllNewsApi()
                }
            }
            Log.d("newsHome", "onViewCreated: swip to refresh")
            refreshLayout.isRefreshing = false
        }
    }
}