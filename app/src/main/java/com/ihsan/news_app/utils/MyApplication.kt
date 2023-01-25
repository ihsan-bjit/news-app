package com.ihsan.news_app.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
class MyApplication : Application() {
    companion object {

        var instance: Context? = null
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
    }
}