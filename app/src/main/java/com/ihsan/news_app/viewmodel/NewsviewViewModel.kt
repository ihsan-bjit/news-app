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

@OptIn(DelicateCoroutinesApi::class)
class NewsviewViewModel(application: Application) : AndroidViewModel(application) {
    //Initialize repository object
    private val repository: NewsRepository

    // setting status
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status

    //Dao Initialize
    private var newsDao: NewsDao

    //keeping news articles
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>?> = _articles

//    private val _home = MutableLiveData<List<Article>>()
//    val home: LiveData<List<Article>> = _home
//    private val _topHeadlines = MutableLiveData<List<Article>>()
//    val topHeadlines: LiveData<List<Article>> = _topHeadlines
//    private val _business = MutableLiveData<List<Article>>()
//    val business: LiveData<List<Article>> = _business
//    private val _entertainment = MutableLiveData<List<Article>>()
//    val entertainment: LiveData<List<Article>> = _entertainment
//    private val _general = MutableLiveData<List<Article>>()
//    val general: LiveData<List<Article>> = _general
//    private val _health = MutableLiveData<List<Article>>()
//    val health: LiveData<List<Article>> = _health
//    private val _science = MutableLiveData<List<Article>>()
//    val science: LiveData<List<Article>> = _science
//    private val _sports = MutableLiveData<List<Article>>()
//    val sports: LiveData<List<Article>> = _sports
//    private val _technology = MutableLiveData<List<Article>>()
//    val technology: LiveData<List<Article>> = _technology

    init {
        //Getting dao instance
        newsDao = NewsDatabase.getDatabase(application).newsDao()
        //Assigning dao object to repository instance
        repository = NewsRepository(newsDao)
    }

    fun getAllNewsApi(): Boolean {
        try {
            GlobalScope.launch {
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
            }

        } catch (e: Exception) {
            return false
        }
        return true
    }

    suspend fun getNewsTableApi(apiArticleList: List<Article>?, category: String) {
        if (apiArticleList == null) {
            Log.d("newsApi", "$category NewsApi Size null return: null")
        }
        val newNewsList = DataConverter().getNewsTable(apiArticleList, category)
        _status.value = NewsApiStatus.DONE
        Log.d("newsNewApi", "$category New News Size: ${newNewsList.size}")
        repository.addNewses(newNewsList)
    }

    fun updateNews(news: NewsTable) {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNews(news)
            }
        }

    }

    fun getBookmarks(): LiveData<List<NewsTable>> {
        return repository.readBookmarksNews()
    }

    fun getAllNewsLocal(): LiveData<List<NewsTable>> {
        return repository.getAllNews()
    }

    fun getTopHeadlineNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readTopHeadlines()
    }

    fun getBusinessNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readBusinessNews()
    }

    fun getEntertainmentNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readEntertainmentNews()
    }

    fun getGeneralNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readGeneralNews()
    }

    fun getHealthNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readHealthNews()
    }

    fun getScienceNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readScienceNews()
    }

    fun getSportsNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readSportsNews()
    }

    fun getTechnologyNewsLocal(): LiveData<List<NewsTable>> {
        return repository.readTechnologyNews()
    }

    private fun getTopHeadLinesApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                getNewsTableApi(
                    NewsApi.retrofitService.getTopHeadlinesApi().articles,
                    "topHeadlines"
                )
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                Log.d("newsCatch", "getTopHeadLinesApi: $e")
            }
        }
    }

    private fun getBusinessNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(
                        NewsApi.retrofitService.getBusinessNewsApi().articles,
                        "business"
                    )
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                    Log.d("newsCatch", "getBusinessNewsApi: $e")
                }
            }
        }

    }

    private fun getEntertainmentNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(
                        NewsApi.retrofitService.getEntertainmentNewsApi().articles,
                        "entertainment"
                    )
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                    Log.d("newsCatch", "getEntertainmentNewsApi: $e")
                }
            }
        }

    }

    private fun getGeneralNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(NewsApi.retrofitService.getGeneralNewsApi().articles, "general")
                } catch (e: java.lang.Exception) {
                    Log.d("newsCatch", "getGeneralNewsApi: $e")
                    _status.value = NewsApiStatus.ERROR
                }
            }
        }

    }

    private fun getHealthNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(NewsApi.retrofitService.getHealthNewsApi().articles, "health")
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                    Log.d("newsCatch", "getHealthNewsApi: $e")
                }
            }
        }

    }

    private fun getScienceNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(NewsApi.retrofitService.getScienceNewsApi().articles, "science")
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                    Log.d("newsCatch", "getScienceNewsApi: $e")
                }
            }
        }

    }

    private fun getSportsNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(NewsApi.retrofitService.getSportsNewsApi().articles, "sports")
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                }
            }
        }

    }

    private fun getTechnologyNewsApi() {
        GlobalScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                _status.value = NewsApiStatus.LOADING
                try {
                    getNewsTableApi(
                        NewsApi.retrofitService.getTechnologyNewsApi().articles,
                        "technology"
                    )
                } catch (e: java.lang.Exception) {
                    _status.value = NewsApiStatus.ERROR
                }
            }
        }

    }
}