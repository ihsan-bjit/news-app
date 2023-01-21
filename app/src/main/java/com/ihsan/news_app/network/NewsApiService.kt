package com.ihsan.news_app.network

import com.ihsan.news_app.model.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL="https://newsapi.org/v2/"
private const val API_KEY="d23ac9e257ea43b0a88772f5fa449792"
private const val PARAMETER="country=us"
private val moshi=Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit=Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
    BASE_URL).build()

interface NewsApiService {
    @GET("top-headlines?$PARAMETER&apiKey=$API_KEY")
    suspend fun getNewsFromApi():News
    @GET("everything?q=&apiKey=$API_KEY")
    suspend fun getNewsSearchApi(searchString:String):News
    @GET("top-headlines?$PARAMETER&apiKey=$API_KEY")
    suspend fun getTopHeadlinesApi():News
    @GET("everything?category=business&apiKey=$API_KEY")
    suspend fun getBusinessNewsApi():News
    @GET("everything?category=entertainment&apiKey=$API_KEY")
    suspend fun getEntertainmentNewsApi():News
    @GET("everything?category=general&apiKey=$API_KEY")
    suspend fun getGeneralNewsApi():News
    @GET("everything?category=health&apiKey=$API_KEY")
    suspend fun getHealthNewsApi():News
    @GET("everything?category=science&apiKey=$API_KEY")
    suspend fun getScienceNewsApi():News
    @GET("everything?category=sports&apiKey=$API_KEY")
    suspend fun getSportsNewsApi():News
    @GET("everything?category=technology&apiKey=$API_KEY")
    suspend fun getTechnologyNewsApi():News
}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}