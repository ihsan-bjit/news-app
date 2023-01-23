package com.ihsan.news_app.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ihsan.news_app.model.NewsTable

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(news: NewsTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewses(news: List<NewsTable>)

    @Update
    suspend fun updateNews(news:NewsTable)

    @Delete
    suspend fun deleteNews(news:NewsTable)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Query("SELECT * FROM news")
    fun readAllNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE isBookmarked='true'")
    fun readBookmarksNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='topHeadlines'")
    fun readTopHeadlines(): LiveData<List<NewsTable>>
    @Query("SELECT * FROM news WHERE category='business'")
    fun readBusinessNews(): LiveData<List<NewsTable>>
    @Query("SELECT * FROM news WHERE category='entertainment'")
    fun readEntertainmentNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='general'")
    fun readGeneralNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='health'")
    fun readHealthNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='science'")
    fun readScienceNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='sports'")
    fun readSportsNews(): LiveData<List<NewsTable>>

    @Query("SELECT * FROM news WHERE category='technology'")
    fun readTechnologyNews(): LiveData<List<NewsTable>>
}