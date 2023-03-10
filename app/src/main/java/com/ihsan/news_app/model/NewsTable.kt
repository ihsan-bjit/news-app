package com.ihsan.news_app.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "news",indices = [Index(value = ["url"], unique = true)])
@Parcelize
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
):Parcelable