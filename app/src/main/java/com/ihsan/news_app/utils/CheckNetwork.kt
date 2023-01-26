package com.ihsan.news_app.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.ihsan.news_app.R
import com.ihsan.news_app.ui.MainActivity


class CheckNetwork {

    fun isOnline(): Boolean {
        val instance = MyApplication.instance
        val connectivityManager =
            instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                Toast.makeText(instance, "ON CELLULAR NETWORK", Toast.LENGTH_SHORT).show()
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                Toast.makeText(instance, "ON WIFI NETWORK", Toast.LENGTH_SHORT).show()
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                Toast.makeText(instance, "ON ETHERNET NETWORK", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        Toast.makeText(instance, "No Internet Connection Available", Toast.LENGTH_SHORT).show()
        return false
    }

    fun checkINTERNETPermission() {
        if (ContextCompat.checkSelfPermission(
                MyApplication.instance,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(Manifest.permission.INTERNET),
                Constant.internetPermissionAccesCode
            )
        } else {
            Toast.makeText(MyApplication.instance, "INTERNET Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun networkReceiver():BroadcastReceiver{
        val networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connectivityManager.activeNetworkInfo
                val isConnected = activeNetwork?.isConnected == true
                if (!isConnected) {
                    Toast.makeText(MyApplication.instance, "Internet is not connected", Toast.LENGTH_SHORT).show()
                }else if(isConnected){
                    Toast.makeText(MyApplication.instance, "Internet is connected", Toast.LENGTH_SHORT).show()
//                    Snackbar.make(View(MainActivity()), "Internet is connected", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        return networkReceiver
    }


}