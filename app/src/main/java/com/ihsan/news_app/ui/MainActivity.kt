package com.ihsan.news_app.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ihsan.news_app.R.id
import com.ihsan.news_app.databinding.ActivityMainBinding
import com.ihsan.news_app.utils.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Network check register and toast at start up
        registerReceiver(CheckNetwork(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        //AirplaneMode check register
        registerReceiver(AirplaneModeReceiver(),IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        //Request SMS permission
        CheckNetwork().requestSmsPermission()
        //check Internet permission
        CheckNetwork().checkINTERNETPermission()
        //Periodic work request call
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(CheckNetwork())
    }
}