package com.ihsan.news_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.R
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.ui.fragment.viewpager.TabLayoutFragmentDirections
import com.ihsan.news_app.utils.Utils
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val articleList: List<NewsTable>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var filterList=articleList
    class ArticleViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        val title: TextView =itemView.findViewById(R.id.title)
        val image:ImageView=itemView.findViewById(R.id.image)
        val description:TextView=itemView.findViewById(R.id.description)
        val source:TextView=itemView.findViewById(R.id.source)
        val btnBookmark:ImageButton=itemView.findViewById(R.id.bookmarks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val root=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return ArticleViewHolder(root)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=filterList[position]
        Log.d("newsAdapter", "onViewCreated adapter: ${filterList.size}")
        holder.title.text=article.title
        holder.description.text=article.description
        val source=article.sourceName?.split(".")
        holder.source.text="${source?.get(0)}·${Utils().timeAgoConverter(article.publishedAt.toString())}"
        if(article.isBookmarked){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
        }
        else{
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_add)
        }

        holder.btnBookmark.setOnClickListener{
            if(!article.isBookmarked){
                holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
                article.isBookmarked=true
                viewModel.updateNews(article)
            }
            else{
                Toast.makeText(context, "Bookmarks Removed from ${article.title}", Toast.LENGTH_SHORT).show()
                article.isBookmarked=false
                viewModel.updateNews(article)
            }
        }

        if(!TextUtils.isEmpty(article.urlToImage))
        {
            Picasso.get().load(article.urlToImage).fit().centerCrop().placeholder(R.drawable.progress_animation).into(holder.image)
        }
        else{
            holder.image.setImageResource(R.drawable.ic_image)
        }
        holder.itemView.setOnClickListener{
            val action= article.let { it1 ->
                TabLayoutFragmentDirections.actionTabLayoutFragmentToDetailNewsViewFragment(it1)
            }
            holder.run { itemView.findNavController().navigate(action) }
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(list: List<NewsTable>) {
        filterList = list
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        val filteredList = ArrayList<NewsTable>()
        for (article in articleList) {
            if (article.title?.lowercase(Locale.ROOT)
                    ?.contains(text.lowercase(Locale.ROOT)) == true
            ) {
                filteredList.add(article)
            }
        }
        filterList(filteredList)
    }
}