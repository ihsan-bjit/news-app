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
import com.ihsan.news_app.adapter.BookmarkAdapter
import com.ihsan.news_app.databinding.FragmentBookmarksBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel


class BookmarksFragment : Fragment() {
    private val viewModel: NewsviewViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding:FragmentBookmarksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBookmarksBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBookmarks().observe(viewLifecycleOwner){
            Log.d("newsBookmark", "onViewCreated: ${it.size}")
            if (it.isNotEmpty()) {
                newsList=it
                recyclerView=view.findViewById(R.id.recyclerview_bookmarks)
                recyclerView.layoutManager= LinearLayoutManager(requireContext())
                recyclerView.adapter= BookmarkAdapter(requireContext(),viewModel,newsList as ArrayList<NewsTable>)
            }else{
                Log.d("newsBookmark", "onViewCreated else roomData: empty")
            }
        }
    }
}