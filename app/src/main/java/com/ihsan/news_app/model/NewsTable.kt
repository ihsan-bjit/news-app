package com.ihsan.news_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceId: String?,
    val sourceName: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val category: String?,
    var isBookmarked:Boolean
)