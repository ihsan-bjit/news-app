package com.ihsan.news_app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.R
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso

class BookmarkAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val newsList: ArrayList<Bookmark>
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        val title=itemView.findViewById<TextView>(R.id.title)
        val image=itemView.findViewById<ImageView>(R.id.image)
        val descripton=itemView.findViewById<TextView>(R.id.description)
        val source=itemView.findViewById<TextView>(R.id.source)
        val btnBookmark=itemView.findViewById<ImageButton>(R.id.bookmarks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val root= LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return BookmarkViewHolder(root)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val news=newsList[position]
        Log.d("TAG", "onViewCreated adapter: ${news.title}")
        holder.title.text=news.title
        holder.descripton.text=news.description
        holder.source.text=news.sourceName
        if(news.isBookmarked){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
        }

        holder.btnBookmark.setOnClickListener{
//            if(article.isBookmarked){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
            news.isBookmarked=false
            viewModel.updateBookmarkNews(news)

//            }
//            else{
//                holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_remove)
//                news.isBookmarked=false
//                viewModel.updateNews(news)
//            }
        }
        Picasso.get().load(news.urlToImage).fit().centerCrop().placeholder(R.drawable.progress_animation).into(holder.image)
    }
}