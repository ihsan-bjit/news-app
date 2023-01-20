package com.ihsan.news_app.roomdb.repository

import androidx.lifecycle.LiveData
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.roomdb.dao.NewsDao

class NewsRepository(private val newsDao:NewsDao) {
    val readAllNews: LiveData<List<NewsTable>> = newsDao.readAllNews()
    val calldao=newsDao.call()

    fun getNews():LiveData<List<NewsTable>>{
        return newsDao.readAllNews()
    }
    suspend fun addNews(news:NewsTable){
        newsDao.addNews(news)
    }
    suspend fun addNewses(news:List<NewsTable>){
        newsDao.addNewses(news)
    }
    suspend fun updateNews(news:NewsTable){
        newsDao.updateNews(news)
    }
    suspend fun deleteNews(news:NewsTable){
        newsDao.deleteNews(news)
    }
    suspend fun deleteAllNews(news:NewsTable){
        newsDao.deleteAll()
    }
    suspend fun readBookmarksNews(){
        newsDao.readBookmarksNews()
    }
    suspend fun readBusinessNews(){
        newsDao.readBusinessNews()
    }
    suspend fun readEntertainmentNews(){
        newsDao.readEntertainmentNews()
    }
    suspend fun readGeneralNews(){
        newsDao.readGeneralNews()
    }
    suspend fun readHealthNews(){
        newsDao.readHealthNews()
    }
    suspend fun readScienceNews(){
        newsDao.readScienceNews()
    }
    suspend fun readSportsNews(){
        newsDao.readSportsNews()
    }
    suspend fun readTechnologyNews(){
        newsDao.readTechnologyNews()
    }
}