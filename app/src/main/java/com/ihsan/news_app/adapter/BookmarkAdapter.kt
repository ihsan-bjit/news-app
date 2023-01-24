package com.ihsan.news_app.adapter

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
import com.ihsan.news_app.viewmodel.NewsviewViewModel
import com.squareup.picasso.Picasso

class BookmarkAdapter(
    private val context: Context,
    private val viewModel: NewsviewViewModel,
    private val articleList: ArrayList<NewsTable>
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    class BookmarkViewHolder(private val binding: View):RecyclerView.ViewHolder(binding){

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
        return  articleList.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val article=articleList[position]
        Log.d("newsBookmarkAdapter", "onViewCreated adapter: ${articleList.size}")
        holder.title.text=article.title
        holder.descripton.text=article.description
        holder.source.text=article.sourceName
        holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_remove)

        holder.btnBookmark.setOnClickListener{
            Toast.makeText(context, "Bookmarks Removed from ${article.title}", Toast.LENGTH_SHORT).show()
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
                TabLayoutFragmentDirections.actionTabLayoutFragmentToDetailNewsViewFragment(it1)
            }
            holder.itemView.findNavController().navigate(action)
            notifyDataSetChanged()
        }
    }
}