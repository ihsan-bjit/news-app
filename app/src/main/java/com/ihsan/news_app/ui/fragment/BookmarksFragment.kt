package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.adapter.BookmarkAdapter
import com.ihsan.news_app.databinding.FragmentBookmarksBinding
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel

class BookmarksFragment : Fragment() {
    private val viewModel: NewsviewViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>
    private lateinit var binding: FragmentBookmarksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarksBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBookmarks().observe(viewLifecycleOwner) {
            Log.d("newsBookmark", "onViewCreated: ${it.size}")
            newsList = it
            recyclerView = binding.recyclerviewBookmarks
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter =
                BookmarkAdapter(requireContext(), viewModel, newsList as ArrayList<NewsTable>)

        }
    }
}