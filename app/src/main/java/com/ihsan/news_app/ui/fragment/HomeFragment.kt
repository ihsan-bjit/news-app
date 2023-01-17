package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.databinding.FragmentHomeBinding
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.viewmodel.NewsviewViewModel


class HomeFragment : Fragment() {
    private lateinit var viewModel: NewsviewViewModel
    private lateinit var recyclerView:RecyclerView
    lateinit var newsList: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this)[NewsviewViewModel::class.java]

        viewModel.articles.observe(viewLifecycleOwner){
            if (it != null) {
                newsList=it
//                Log.d("TAG", "onViewCreated home newsList: $newsList")
                recyclerView=view.findViewById(R.id.recyclerview)
                recyclerView.layoutManager=LinearLayoutManager(requireContext())
                recyclerView.adapter=ArticleAdapter(requireContext(),viewModel,newsList as ArrayList<Article>)
            }
//            Log.d("TAG", "onViewCreated home: $it")
        }

    }

}