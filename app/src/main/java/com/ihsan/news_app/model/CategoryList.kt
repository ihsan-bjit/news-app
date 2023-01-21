package com.ihsan.news_app.model

import androidx.lifecycle.LiveData

data class CategoryList(
    var topHeadLines:LiveData<List<NewsTable>?>,
    val business:LiveData<List<NewsTable>?>,
    val entertainment:LiveData<List<NewsTable>?>,
    val general:LiveData<List<NewsTable>?>,
    val health:LiveData<List<NewsTable>?>,
    val science:LiveData<List<NewsTable>?>,
    val sports:LiveData<List<NewsTable>?>,
    val technology:LiveData<List<NewsTable>?>,
)
