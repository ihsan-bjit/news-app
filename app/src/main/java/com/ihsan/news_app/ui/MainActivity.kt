package com.ihsan.news_app.ui

import android.Manifest
import android.content.pm.PackageManager
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
import com.ihsan.news_app.R.id
import com.ihsan.news_app.databinding.ActivityMainBinding
import com.ihsan.news_app.utils.Constant
import com.ihsan.news_app.utils.CheckNetwork
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
        checkINTERNETPermission()
        CheckNetwork().isOnline()
        setPeriodicWorkRequest()

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

    private fun setPeriodicWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)
        val dataLoad = PeriodicWorkRequest
            .Builder(DataReloadWorker::class.java, 15, TimeUnit.MINUTES)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .addTag("ReloadData")
            .build()
        workManager.enqueueUniquePeriodicWork(
            "ReloadData",
            ExistingPeriodicWorkPolicy.REPLACE,
            dataLoad
        )
    }

    private fun checkINTERNETPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                Constant.internetPermissionAccesCode
            )
        } else {
            Toast.makeText(this, "INTERNET Permission already given", Toast.LENGTH_SHORT).show()
        }
    }
}