package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ihsan.news_app.R
import com.ihsan.news_app.adapter.ArticleAdapter
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel

class GeneralFragment : Fragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: NewsviewViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var newsList: List<NewsTable>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this)[NewsviewViewModel::class.java]

        viewModel.getGeneralNewsLocal().observe(viewLifecycleOwner){
            if (it != null) {
                newsList=it
//                Log.d("TAG", "onViewCreated home newsList: $newsList")
                recyclerView=view.findViewById(R.id.recyclerview_general)
                recyclerView.layoutManager= LinearLayoutManager(requireContext())
                recyclerView.adapter= ArticleAdapter(requireContext(),viewModel,newsList as ArrayList<NewsTable>)
            }
            else{
                Toast.makeText(requireContext(), "Data not fetched from api", Toast.LENGTH_SHORT).show()
            }
//            Log.d("TAG", "onViewCreated home: $it")
        }
    }
}