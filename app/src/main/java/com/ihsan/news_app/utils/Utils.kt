package com.ihsan.news_app.utils

import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    fun refreshMessage() {
        Toast.makeText(MyApplication.instance, "Reloading News", Toast.LENGTH_SHORT).show()
    }

    fun logNtoast(tag: String, message: String) {
        Toast.makeText(MyApplication.instance, "tag: $tag message: $message", Toast.LENGTH_SHORT).show()
        Log.d(tag, message)
    }

    fun timeAgoConverter(timestamp: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = dateFormat.parse(timestamp)
        val currentTime = Calendar.getInstance().timeInMillis
        var hoursAgo = (currentTime - date.time) / (60 * 60 * 1000)
        var yMDHAgo = " "
        if (hoursAgo.equals(0)) {
            return "1h> ago"
        }
        if (hoursAgo >= 365 * 24) {
            yMDHAgo += " ${hoursAgo / 365 * 24}y"
            hoursAgo %= 365 * 24
        }
        if (hoursAgo >= 30 * 24) {
            yMDHAgo += " ${hoursAgo / 30 * 24}m"
            hoursAgo %= 30 * 24
        }
        if (hoursAgo >= 24) {
            yMDHAgo += " ${hoursAgo / 24}d"
            hoursAgo %= 24
        }
        if (hoursAgo > 0) {
            yMDHAgo += " ${hoursAgo}h"
        }
        return "$yMDHAgo ago"
    }
}