package com.ihsan.news_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.News
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.model.Source
import com.ihsan.news_app.network.NewsApi
import com.ihsan.news_app.roomdb.dao.NewsDao
import com.ihsan.news_app.roomdb.data.DataConverter
import com.ihsan.news_app.roomdb.db.NewsDatabase
import com.ihsan.news_app.roomdb.repository.NewsRepository
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsviewViewModel(application: Application): AndroidViewModel(application) {
    //Initialize repository object
    val repository:NewsRepository
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> = _articles

    // setting status
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private lateinit var newsDao:NewsDao
    // getting news from room db
    //private val _readAllNews = MutableLiveData<List<Article>>()
    //val readAllNews: LiveData<List<Article>> /*= _readAllNews*/

    init {
        //Getting dao instance
         newsDao = NewsDatabase.getDatabase(application).newsDao()

        //Assigning dao object to repository instance
        repository = NewsRepository(newsDao)
        Log.d("TAG", "is called: ${repository.readAllNews.value}")
        Log.d("TAG", "repo getnws :${repository.getNews().value} ")
        val roomNews = repository.readAllNews.value
        Log.d("TAG", "roomNews: ${repository.readAllNews.value}")

        // converting room data to Article
        if (roomNews != null) {
            val roomNewsToArticle: List<Article> = DataConverter().getArticle(roomNews)
            Log.d("TAG", "roomNewsToArticle: $roomNewsToArticle ")

//            readAllNews.value = roomNewsToArticle
            Log.d("TAG", "roomNewsToArticle: $roomNewsToArticle ")
        } else {
//            readAllNews.value= listOf<Article>(Article("","","","", Source("",""),"","",""))
//            Toast.makeText(context, "xfs", Toast.LENGTH_SHORT).show()
        }
        getApiNews()
    }
    fun getAllNews():LiveData<List<NewsTable>>{
        return repository.getNews()
    }

    private fun getApiNews(){
        viewModelScope.launch{
            _status.value=NewsApiStatus.LOADING
            try {
                _news.value=NewsApi.retrofitService.getNewsFromApi()
                _articles.value= _news.value!!.articles

//                Log.d("TAG", "getNews: ${_news.value!!.articles?.size}")
                _status.value=NewsApiStatus.DONE

                Log.d("TAG", "getApiNews: ${repository.readAllNews.value}")

                val responseToRoomFormat= DataConverter().getNewsTable(_articles.value,"all")
                repository.addNewses(responseToRoomFormat)
            }
            catch (e: java.lang.Exception){
                _status.value=NewsApiStatus.ERROR
                _news.value=News(null,"error",0)
            }
        }
    }
}