package com.ihsan.news_app.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ihsan.news_app.R
import com.ihsan.news_app.R.id
import com.ihsan.news_app.databinding.ActivityMainBinding
import com.ihsan.news_app.utils.*
import com.ihsan.news_app.worker.DataReloadWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Network check and toast at start up
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        requestSmsPermission()
        registerReceiver(CheckNetwork(), filter)
        CheckNetwork().checkINTERNETPermission()
        WorkRequest().setPeriodicWorkRequest()

        val bottomNavigation: BottomNavigationView = binding.bottomNav
        navController = findNavController(id.fragment_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                id.tabLayoutFragment, id.bookmarksFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigation.setupWithNavController(navController)
    }

    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1000)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}