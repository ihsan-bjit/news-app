package com.ihsan.news_app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
        //lateinit var activity: Activity
        private set
    }

    override fun onCreate() {
        super.onCreate()
        //activity=this as Activity
        instance = this
    }
}