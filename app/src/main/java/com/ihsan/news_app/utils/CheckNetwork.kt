package com.ihsan.news_app.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.ihsan.news_app.ui.MainActivity
import java.lang.Thread.sleep

data class Network(
    var connection: NetworkCapabilities?,
    var wifi: Boolean,
    var cellular: Boolean,
    var ethernet: Boolean
)

class CheckNetwork : BroadcastReceiver() {
    companion object {
        val networkStatus = MutableLiveData(
            Network(
                connection = null, wifi = false, cellular = false, ethernet = false
            )
        )
    }

    override fun onReceive(context: Context, intent: Intent) {
        Thread{
            sleep(1000)
        }
        val instance = MyApplication.instance
        val connectivityManager =
            instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        Log.d("Internet", "onReceive: $capabilities")
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)&& !networkStatus.value!!.wifi) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                Toast.makeText(instance, "Internet connected On WIFI", Toast.LENGTH_SHORT).show()
                networkStatus.value!!.wifi = true
                networkStatus.value!!.cellular=false
            } else {
                networkStatus.value!!.wifi = false
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)&& !networkStatus.value!!.cellular) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                Toast.makeText(instance, "Internet connected On CELLULAR", Toast.LENGTH_SHORT)
                    .show()
                networkStatus.value!!.cellular = true
            } else {
                networkStatus.value!!.cellular = false
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                Toast.makeText(instance, "Internet connected On ETHERNET", Toast.LENGTH_SHORT)
                    .show()
                networkStatus.value!!.ethernet = true
            } else {
                networkStatus.value!!.ethernet = false
            }
        } else if (capabilities == null) {

            Toast.makeText(
                MyApplication.instance, "No Internet Connection", Toast.LENGTH_SHORT
            ).show()
            Log.d("Internet", "onReceive: Not connected")
        }
    }
    
    fun checkINTERNETPermission() {
        if (ContextCompat.checkSelfPermission(
                MyApplication.instance, Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(Manifest.permission.INTERNET),
                Constant.internetPermissionAccesCode
            )
        } else {
            Toast.makeText(
                MyApplication.instance, "INTERNET Permission Granted", Toast.LENGTH_SHORT
            ).show()
        }
    }
}