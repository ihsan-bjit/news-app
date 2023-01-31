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
import com.google.android.material.snackbar.Snackbar
import com.ihsan.news_app.R
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.ui.fragment.BookmarksFragmentDirections
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NotifyDataSetChanged")
class BookmarkAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val articleList: ArrayList<NewsTable>
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    class BookmarkViewHolder(private val binding: View):RecyclerView.ViewHolder(binding){

        val title:TextView=itemView.findViewById(R.id.title)
        val image:ImageView=itemView.findViewById(R.id.image)
        val description:TextView=itemView.findViewById(R.id.description)
        val source:TextView=itemView.findViewById(R.id.source)
        val btnBookmark:ImageButton=itemView.findViewById(R.id.bookmarks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val root= LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return BookmarkViewHolder(root)
    }

    override fun getItemCount(): Int {
        return  articleList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val article=articleList[position]
        Log.d("newsBookmarkAdapter", "onViewCreated adapter: ${articleList.size}")
        holder.title.text=article.title
        holder.description.text=article.description
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date= dateFormat.parse(article.publishedAt)
        val cal=Calendar.getInstance()
        holder.source.text= "${article.sourceName}"
        holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_remove)

        holder.btnBookmark.setOnClickListener{
            Snackbar.make(it, "Bookmarks Removed from ${article.title}", Snackbar.LENGTH_SHORT).show()
            article.isBookmarked=false
            viewModel.updateNews(article)
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
                BookmarksFragmentDirections.actionBookmarksFragmentToDetailNewsViewFragment(it1)
            }
            holder.itemView.findNavController().navigate(action)
            notifyDataSetChanged()
        }
    }
}