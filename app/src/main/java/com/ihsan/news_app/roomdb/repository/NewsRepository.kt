package com.ihsan.news_app.roomdb.repository

import androidx.lifecycle.LiveData
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.roomdb.dao.NewsDao

class NewsRepository(private val newsDao:NewsDao) {
    val readAllNews: LiveData<List<NewsTable>> = newsDao.readAllNews()

    suspend fun addNews(news:NewsTable){
        newsDao.addNews(news)
    }
}