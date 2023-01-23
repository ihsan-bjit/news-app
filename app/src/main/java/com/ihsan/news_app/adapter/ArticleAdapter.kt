package com.ihsan.news_app.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.R
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso

class ArticleAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val articleList: ArrayList<NewsTable>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        val title=itemView.findViewById<TextView>(R.id.title)
        val image=itemView.findViewById<ImageView>(R.id.image)
        val descripton=itemView.findViewById<TextView>(R.id.description)
        val source=itemView.findViewById<TextView>(R.id.source)
        val btnBookmark=itemView.findViewById<ImageButton>(R.id.bookmarks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val root=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return ArticleViewHolder(root)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=articleList[position]
        Log.d("TAG", "onViewCreated adapter: ${article.title}")
        holder.title.text=article.title
        holder.descripton.text=article.description
        holder.source.text=article.sourceName
        if(article.isBookmarked){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
            holder.btnBookmark.alpha=.5f

        }

        holder.btnBookmark.setOnClickListener{
            if(!article.isBookmarked){
                holder.btnBookmark.alpha=.5f
                holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_added)
                article.isBookmarked=true
                viewModel.updateNews(article)
            }
            else{
                Toast.makeText(context, "false", Toast.LENGTH_SHORT).show()
                holder.btnBookmark.alpha=1f
                holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_add)
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

        
    }
}