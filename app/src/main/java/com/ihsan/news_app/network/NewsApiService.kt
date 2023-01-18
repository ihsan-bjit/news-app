package com.ihsan.news_app.network

import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL="https://newsapi.org/v2/"

private val moshi=Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit=Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
    BASE_URL).build()

interface NewsApiService {
    @GET("everything?q=tesla&apiKey=d23ac9e257ea43b0a88772f5fa449792")
    suspend fun getNews():News
}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}