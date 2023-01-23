package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ihsan.news_app.R
import com.ihsan.news_app.ui.fragment.viewpager.TabLayoutFragmentDirections
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail_news_view.*
import kotlinx.android.synthetic.main.news_item.view.*

class DetailNewsViewFragment : Fragment() {
    private val args: DetailNewsViewFragmentArgs by navArgs()
    private val viewModel: NewsviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_news_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article=args.news

        if(!TextUtils.isEmpty(article.urlToImage))
        {
            Picasso.get().load(article.urlToImage).fit().placeholder(R.drawable.progress_animation).into(detail_image)
        }
        else{
            detail_image.setImageResource(R.drawable.ic_image)
        }
        detail_title.text=article.title
        detail_description.text=article.content
        detail_source.text=article.sourceName
        btn_card_view_online.setOnClickListener{
            val action= article?.let { it1 ->
                DetailNewsViewFragmentDirections.actionDetailNewsViewFragmentToWebviewNewsFragment(it1.url.toString())
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
        if(!article.isBookmarked){
            detail_bookmarks.setImageResource(R.drawable.ic_bookmark_add)
        }
        else{
            detail_bookmarks.setImageResource(R.drawable.ic_bookmark_remove)
        }
        detail_bookmarks.setOnClickListener{
            if(!article.isBookmarked){
                detail_bookmarks.setImageResource(R.drawable.ic_bookmark_remove)
                article.isBookmarked=true
                viewModel.updateNews(article)
            }
            else{
                Toast.makeText(context, "Bookmarks Removed from ${article.title}", Toast.LENGTH_SHORT).show()
                detail_bookmarks.setImageResource(R.drawable.ic_bookmark_add)
                article.isBookmarked=false
                viewModel.updateNews(article)
            }
        }
    }
}