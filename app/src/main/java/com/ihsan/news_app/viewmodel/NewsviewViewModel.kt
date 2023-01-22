package com.ihsan.news_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ihsan.news_app.model.*
import com.ihsan.news_app.network.NewsApi
import com.ihsan.news_app.roomdb.dao.NewsDao
import com.ihsan.news_app.roomdb.data.DataConverter
import com.ihsan.news_app.roomdb.db.NewsDatabase
import com.ihsan.news_app.roomdb.repository.NewsRepository
import kotlinx.coroutines.*

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsviewViewModel(application: Application) : AndroidViewModel(application) {
    //Initialize repository object
    val repository: NewsRepository
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> = _articles
    private val _business = MutableLiveData<List<Article>?>()
    val business: LiveData<List<Article>?> = _business

    // setting status
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private lateinit var newsDao: NewsDao

    init {
        //Getting dao instance
        newsDao = NewsDatabase.getDatabase(application).newsDao()

        //Assigning dao object to repository instance
        repository = NewsRepository(newsDao)
        //getApiNews()
       // getTopHeadLinesApi()
      getAllNewsApi()
    }


    private fun getAllNewsApi():Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                getTopHeadLinesApi()
                getBusinessNewsApi()
                getEntertainmentNewsApi()
                getGeneralNewsApi()
                getHealthNewsApi()
                getScienceNewsApi()
                getSportsNewsApi()
                getTechnologyNewsApi()
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun getOnlyNewNews(localNewsList:List<NewsTable>?, apiArticleList:List<Article>?, category: String):List<NewsTable>?{
        if (apiArticleList==null)
        {
            Log.d("news", "$category NewsApi Size null return: ${apiArticleList?.size}")
            return listOf()
        }

        val newNewsList:List<NewsTable>
        Log.d("news", "$category NewsApi Size: ${apiArticleList?.size}")
        Log.d("news", "$category NewsLocal Size: ${localNewsList?.size}")

        if (localNewsList!=null){
            val localArticle:List<Article> = DataConverter().getArticle(localNewsList)
            var newArticleList= mutableListOf<Article>()
            apiArticleList.map {
                if (!localArticle.contains(it)) {
                    newArticleList.add(it)
                }
            }
            newNewsList=DataConverter().getNewsTable(newArticleList as List<Article>,category)
        }
        else {
            newNewsList=DataConverter().getNewsTable(apiArticleList,category)
        }
        _status.value = NewsApiStatus.DONE
        Log.d("news", "$category New News Size: ${newNewsList?.size}")

        return newNewsList
    }

    fun getBookmarks(): LiveData<List<NewsTable>> { return repository.readBookmarksNews() }
    fun getAllNewsLocal(): LiveData<List<NewsTable>> { return repository.getAllNews() }
    fun getTopHeadlineNewsLocal(): LiveData<List<NewsTable>> { return repository.readTopHeadlines() }
    fun getBusinessNewsLocal(): LiveData<List<NewsTable>> { return repository.readBusinessNews() }
    fun getEntertainmentNewsLocal(): LiveData<List<NewsTable>> { return repository.readEntertainmentNews() }
    fun getGeneralNewsLocal(): LiveData<List<NewsTable>> { return repository.readGeneralNews() }
    fun getHealthNewsLocal(): LiveData<List<NewsTable>> { return repository.readHealthNews() }
    fun getScienceNewsLocal(): LiveData<List<NewsTable>> { return repository.readScienceNews() }
    fun getSportsNewsLocal(): LiveData<List<NewsTable>> { return repository.readSportsNews() }
    fun getTechnologyNewsLocal(): LiveData<List<NewsTable>> { return repository.readTechnologyNews() }

    private fun getTopHeadLinesApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readTopHeadlines().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"topHeadlines")
                repository.addNewses(newNews!!)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getBusinessNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readBusinessNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"business")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getEntertainmentNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readEntertainmentNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"entertainment")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getGeneralNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readGeneralNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"general")
                repository.addNewses(newNews!!)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getHealthNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readHealthNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"health")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getScienceNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readScienceNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"science")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getSportsNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readSportsNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"sports")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getTechnologyNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(repository.readTechnologyNews().value,NewsApi.retrofitService.getTopHeadlinesApi().articles,"technology")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }
}