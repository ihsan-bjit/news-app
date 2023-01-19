package com.ihsan.news_app.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.NewsTable

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news:NewsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewses(news:List<NewsTable>)

    @Query("SELECT * FROM news")
    fun readAllNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='sports'")
    fun readSportsNews(): LiveData<List<NewsTable>>
}