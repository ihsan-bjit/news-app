package com.ihsan.news_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.News
import com.ihsan.news_app.network.NewsApi
import com.ihsan.news_app.roomdb.data.DataConverter
import com.ihsan.news_app.roomdb.db.NewsDatabase
import com.ihsan.news_app.roomdb.repository.NewsRepository
import kotlinx.coroutines.*

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsviewViewModel(application: Application): AndroidViewModel(application) {
    lateinit var repository:NewsRepository
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> = _articles
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status

    init {
        val newsDao= NewsDatabase.getDatabase(application).newsDao()
        repository= NewsRepository(newsDao)
        getNews()

    }
    private fun getNews(){
        viewModelScope.launch{
            _status.value=NewsApiStatus.LOADING
            try {
                _news.value=NewsApi.retrofitService.getNews()
                _articles.value= _news.value!!.articles
                Log.d("TAG", "getNews: ${_news.value!!.articles?.size}")
                _status.value=NewsApiStatus.DONE
                val responseToRoomFormat= DataConverter().getNewsTable(_articles.value,"all")
//                repository.addNewses(responseToRoomFormat)
                repository.addNews(responseToRoomFormat[0])
            }catch (e: java.lang.Exception){
                _status.value=NewsApiStatus.ERROR
                _news.value=News(null,"error",0)
            }
        }
    }
}