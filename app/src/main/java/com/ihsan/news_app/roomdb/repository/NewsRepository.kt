package com.ihsan.news_app.roomdb.repository

import androidx.lifecycle.LiveData
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.roomdb.dao.NewsDao

class NewsRepository(private val newsDao: NewsDao) {
    val readAllNews: LiveData<List<NewsTable>> = newsDao.readAllNews()

    fun getAllNews(): LiveData<List<NewsTable>> {
        return newsDao.readAllNews()
    }

    suspend fun addNews(news: NewsTable) {
        newsDao.addNews(news)
    }

    suspend fun addNewses(news: List<NewsTable>) {
        newsDao.addNewses(news)
    }

    suspend fun updateNews(news: NewsTable) {
        newsDao.updateNews(news)
    }

    suspend fun deleteNews(news: NewsTable) {
        newsDao.deleteNews(news)
    }

    suspend fun deleteAllNews(news: NewsTable) {
        newsDao.deleteAll()
    }

    fun readBookmarksNews(): LiveData<List<NewsTable>> {
        return newsDao.readBookmarksNews()
    }

    fun readTopHeadlines(): LiveData<List<NewsTable>> {
        return newsDao.readTopHeadlines()
    }

    fun readBusinessNews(): LiveData<List<NewsTable>> {
        return newsDao.readBusinessNews()
    }

    fun readEntertainmentNews(): LiveData<List<NewsTable>> {
        return newsDao.readEntertainmentNews()
    }

    fun readGeneralNews(): LiveData<List<NewsTable>> {
        return newsDao.readGeneralNews()
    }

    fun readHealthNews(): LiveData<List<NewsTable>> {
        return newsDao.readHealthNews()
    }

    fun readScienceNews(): LiveData<List<NewsTable>> {
        return newsDao.readScienceNews()
    }

    fun readSportsNews(): LiveData<List<NewsTable>> {
        return newsDao.readSportsNews()
    }

    fun readTechnologyNews(): LiveData<List<NewsTable>> {
        return newsDao.readTechnologyNews()
    }
}