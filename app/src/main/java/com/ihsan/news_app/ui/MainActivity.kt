package com.ihsan.news_app.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ihsan.news_app.R.*
import com.ihsan.news_app.databinding.ActivityMainBinding
import com.ihsan.news_app.ui.fragment.BookmarksFragmentDirections
import com.ihsan.news_app.ui.fragment.viewpager.TabLayoutFragmentDirections


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isOnline(this)

        val navHostFragment = supportFragmentManager.findFragmentById(id.fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController)
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                id.home-> {
                    val action = BookmarksFragmentDirections.actionBookmarksFragmentToTabLayoutFragment()
                    navController.navigate(action)
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                id.bookmarks-> {
                    val action = TabLayoutFragmentDirections.actionTabLayoutFragmentToBookmarksFragment()
                    navController.navigate(action)
                    Toast.makeText(this, "Bookmarks", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    Toast.makeText(this, "ON CELLULAR NETWORK", Toast.LENGTH_SHORT).show()
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    Toast.makeText(this, "ON WIFI NETWORK", Toast.LENGTH_SHORT).show()
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    Toast.makeText(this, "ON ETHERNET NETWORK", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_SHORT).show()
        return false
    }
}