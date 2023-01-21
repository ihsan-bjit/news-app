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
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsviewViewModel(application: Application) : AndroidViewModel(application) {
    //Initialize repository object
    val repository: NewsRepository
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> = _articles

    // setting status
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private lateinit var newsDao: NewsDao

    // getting news from room db
    private val readAllNews = MutableLiveData<List<Article>>()

    init {
        //Getting dao instance
        newsDao = NewsDatabase.getDatabase(application).newsDao()

        //Assigning dao object to repository instance
        repository = NewsRepository(newsDao)
        getApiNews()
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

    private fun getApiNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getNewsFromApi().articles
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "all")
                repository.addNewses(responseToRoomFormat)

                Log.d("viewmodel", "api news size News Size: ${_news.value!!.articles?.size}")
            }
            catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getTopHeadLinesApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getTopHeadlinesApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat =
                    DataConverter().getNewsTable(_articles.value, "topHeadlines")
                repository.addNewses(responseToRoomFormat)
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
                _articles.value = NewsApi.retrofitService.getBusinessNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "business")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getEntertainmentNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getEntertainmentNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "entertainment")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getGeneralNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getGeneralNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "general")
                repository.addNewses(responseToRoomFormat)
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
                _articles.value = NewsApi.retrofitService.getHealthNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "health")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getScienceNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getScienceNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "science")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getSportsNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getSportsNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat = DataConverter().getNewsTable(_articles.value, "sports")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }

    private fun getTechnologyNewsApi() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _articles.value = NewsApi.retrofitService.getTechnologyNewsApi().articles

                Log.d("All", "All News Size: ${_news.value!!.articles?.size}")
                _status.value = NewsApiStatus.DONE

                val responseToRoomFormat =
                    DataConverter().getNewsTable(_articles.value, "technology")
                repository.addNewses(responseToRoomFormat)
            } catch (e: java.lang.Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = News(null, "error", 0)
            }
        }
    }
}