package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentHomeBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel


class HomeFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    val viewModel: NewsviewViewModel by viewModels()
    private lateinit var recyclerView:RecyclerView
    lateinit var newsList: List<NewsTable>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment
//        viewModel = ViewModelProvider(requireActivity())[NewsviewViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.swipeLayout)
        viewModel.getAllNewsLocal().observe(viewLifecycleOwner){
            Log.d("home", "onViewCreated: $it")
            if (it != null) {
                newsList=it
//                Log.d("TAG", "onViewCreated home newsList: $newsList")
                recyclerView=view.findViewById(R.id.recyclerview)
                recyclerView.layoutManager=LinearLayoutManager(requireContext())
                recyclerView.adapter=ArticleAdapter(requireContext(),viewModel,newsList as ArrayList<NewsTable>)
            }else{
                Log.d("home", "onViewCreated else roomData: $it")
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.getAllNewsLocal().observe(viewLifecycleOwner){
                Log.d("news3", "onRefresh: ${it.size}")
                viewModel.getAllNewsApi(it)
            }
            Log.d("news3", "onViewCreated: swip to refresh")
            refreshLayout.isRefreshing = false
        }
    }
}