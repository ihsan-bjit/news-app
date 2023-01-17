package com.ihsan.news_app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.news_app.R
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso

class ArticleAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        val title=itemView.findViewById<TextView>(R.id.title)
        val image=itemView.findViewById<ImageView>(R.id.image)
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
        Picasso.get().load(article.urlToImage).into(holder.image)
    }
}