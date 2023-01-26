package com.ihsan.news_app.utils

import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    fun refreshMessage(){
        Toast.makeText(MyApplication.instance, "Reloading News", Toast.LENGTH_SHORT).show()
    }
    fun timeAgoConverter(timestamp:String):String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = dateFormat.parse(timestamp)
        val currentTime = Calendar.getInstance().timeInMillis
        val hoursAgo = (currentTime - date.time) / (60 * 60 * 1000)
        return "$hoursAgo hours ago"
    }


}