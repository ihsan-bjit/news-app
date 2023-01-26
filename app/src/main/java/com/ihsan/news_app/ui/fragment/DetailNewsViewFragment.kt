package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ihsan.news_app.R
import com.ihsan.news_app.databinding.FragmentDetailNewsViewBinding
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso


class DetailNewsViewFragment : Fragment() {
    private val args: DetailNewsViewFragmentArgs by navArgs()
    private lateinit var viewModel: NewsviewViewModel
    private lateinit var binding:FragmentDetailNewsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailNewsViewBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this)[NewsviewViewModel::class.java]
        val article=args.news

        if(!TextUtils.isEmpty(article.urlToImage))
        {
            Picasso.get().load(article.urlToImage).fit().placeholder(R.drawable.progress_animation).into(binding.detailImage)
        }
        else{
            binding.detailImage.setImageResource(R.drawable.ic_image)
        }
        binding.detailTitle.text=article.title
        binding.detailDescription.text=article.content
        binding.detailSource.text=article.sourceName
        binding.btnCardViewOnline.setOnClickListener{
            val action= article.let { it1 ->
                DetailNewsViewFragmentDirections.actionDetailNewsViewFragmentToWebviewNewsFragment(it1.url.toString())
            }
            findNavController().navigate(action)
        }
        if(!article.isBookmarked){
            binding.detailBookmarks.setImageResource(R.drawable.ic_bookmark_add)
        }
        else{
            binding.detailBookmarks.setImageResource(R.drawable.ic_bookmark_remove)
        }
        binding.detailBookmarks.setOnClickListener{

            if(!article.isBookmarked){
                binding.detailBookmarks.setImageResource(R.drawable.ic_bookmark_remove)
                article.isBookmarked=true
                viewModel.updateNews(article)
            }
            else{
                Snackbar.make(it, "Bookmarks Removed from ${article.title}", Snackbar.LENGTH_SHORT).show()
                binding.detailBookmarks.setImageResource(R.drawable.ic_bookmark_add)
                article.isBookmarked=false
                viewModel.updateNews(article)
            }
        }
    }

}