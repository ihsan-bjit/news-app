package com.ihsan.news_app.utils

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ihsan.news_app.worker.DataReloadWorker
import java.util.concurrent.TimeUnit

class WorkRequest {
    fun setPeriodicWorkRequest() {
        val workManager = WorkManager.getInstance(MyApplication.instance)
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
}