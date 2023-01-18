package com.ihsan.news_app.roomdb.repository

import androidx.lifecycle.LiveData
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.roomdb.dao.NewsDao

class NewsRepository(private val newsDao:NewsDao) {
    val readAllNews: LiveData<List<Article>> = newsDao.readAllNews()

    suspend fun addNews(news:Article){
        newsDao.addNews(news)
    }
}