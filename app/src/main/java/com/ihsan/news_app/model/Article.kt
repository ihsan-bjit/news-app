package com.ihsan.news_app.model

import androidx.room.Entity

@Entity(tableName = "news")
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)