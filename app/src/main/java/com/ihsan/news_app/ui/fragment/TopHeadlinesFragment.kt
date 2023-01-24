package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentTopHeadlinesBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import kotlinx.coroutines.launch

class TopHeadlinesFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: NewsviewViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding:FragmentTopHeadlinesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTopHeadlinesBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = binding.swipeLayout
        viewModel= ViewModelProvider(this)[NewsviewViewModel::class.java]

        viewModel.getTopHeadlineNewsLocal().observe(viewLifecycleOwner){
            if (it != null) {
                newsList=it
                Log.d("newsTopHeadlines", "onViewCreated home newsList: ${newsList.size}")
                recyclerView=binding.recyclerviewTopHeadlines
                recyclerView.layoutManager= LinearLayoutManager(requireContext())
                recyclerView.adapter= ArticleAdapter(requireContext(),viewModel,newsList as ArrayList<NewsTable>)
            }
            else{
                Toast.makeText(requireContext(), "Data not fetched from api", Toast.LENGTH_SHORT).show()
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.getAllNewsLocal().observe(viewLifecycleOwner) {
                viewModel.viewModelScope.launch {
                    viewModel.getAllNewsApi()
                }
            }
            Log.d("newsTopHeadlines", "onViewCreated: swipe to refresh")
            refreshLayout.isRefreshing = false
        }
    }
}