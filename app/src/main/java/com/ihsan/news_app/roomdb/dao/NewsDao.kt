package com.ihsan.news_app.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihsan.news_app.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news:Article)

    @Query("SELECT * FROM news")
    fun readAllNews(): LiveData<List<Article>>
}