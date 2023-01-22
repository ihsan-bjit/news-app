package com.ihsan.news_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
    private val repository: NewsRepository
    // setting status
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private var newsDao: NewsDao

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>?> = _articles

    private val _home = MutableLiveData<List<Article>>()
    val home: LiveData<List<Article>> = _home
    private val _topHeadlines = MutableLiveData<List<Article>>()
    val topHeadlines: LiveData<List<Article>> = _topHeadlines
    private val _business = MutableLiveData<List<Article>>()
    val business: LiveData<List<Article>> = _business
    private val _entertainment = MutableLiveData<List<Article>>()
    val entertainment: LiveData<List<Article>> = _entertainment
    private val _general = MutableLiveData<List<Article>>()
    val general: LiveData<List<Article>> = _general
    private val _health = MutableLiveData<List<Article>>()
    val health: LiveData<List<Article>> = _health
    private val _science = MutableLiveData<List<Article>>()
    val science: LiveData<List<Article>> = _science
    private val _sports = MutableLiveData<List<Article>>()
    val sports: LiveData<List<Article>> = _sports
    private val _technology = MutableLiveData<List<Article>>()
    val technology: LiveData<List<Article>> = _technology



    init {
        //Getting dao instance
        newsDao = NewsDatabase.getDatabase(application).newsDao()

        //Assigning dao object to repository instance
        repository = NewsRepository(newsDao)

//        getAllNewsLocalDB()
        //getAllNewsApi()
    }

    private fun getAllNewsLocalDB():Boolean
    {
        Log.d("news2", "getAllNewsLocalDB: ${repository.readAllNews.value?.size}")
        return true
    }

    fun getAllNewsApi(localNews:List<NewsTable>):Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                getTopHeadLinesApi(localNews)
                getBusinessNewsApi(localNews)
                getEntertainmentNewsApi(localNews)
                getGeneralNewsApi(localNews)
                getHealthNewsApi(localNews)
                getScienceNewsApi(localNews)
                getSportsNewsApi(localNews)
                getTechnologyNewsApi(localNews)
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

    private fun getTopHeadLinesApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                repository.readTopHeadlines()
                Log.d("news1", "getTopHeadLinesApi localrepo: ${repository.readTopHeadlines().value?.size}")
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"topHeadlines")
                repository.addNewses(newNews!!)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getBusinessNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"business")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getEntertainmentNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"entertainment")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getGeneralNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"general")
                repository.addNewses(newNews!!)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getHealthNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"health")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getScienceNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"science")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getSportsNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"sports")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    private fun getTechnologyNewsApi(localNews:List<NewsTable>) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val newNews=getOnlyNewNews(localNews,NewsApi.retrofitService.getTopHeadlinesApi().articles,"technology")
                repository.addNewses(newNews!!)
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
            }
        }
    }
}