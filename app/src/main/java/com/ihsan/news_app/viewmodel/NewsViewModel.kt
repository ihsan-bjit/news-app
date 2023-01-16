package com.ihsan.news_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsan.news_app.model.News
import com.ihsan.news_app.network.NewsApi
import kotlinx.coroutines.*

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsViewModel: ViewModel() {
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status

    init {
        getNews()
    }

    private fun getNews(){
        viewModelScope.launch {
            _status.value=NewsApiStatus.LOADING
            try {
                _news.value=NewsApi.retrofitService.getNews()
                Log.d("TAG", "getNews: ${_news.value!!.articles?.size}")
                _status.value=NewsApiStatus.DONE
            }catch (e: java.lang.Exception){
                _status.value=NewsApiStatus.ERROR
                _news.value=News(null,"error",0)
            }
        }
    }
}