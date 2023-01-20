package com.ihsan.news_app.roomdb.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.roomdb.dao.NewsDao

@Database(entities = [NewsTable::class], version=6, exportSchema = false)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun newsDao():NewsDao
    companion object{
        @Volatile
        private var INSTANCE: NewsDatabase?=null

        fun getDatabase(context: Context): NewsDatabase{
            val tempInstance= INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE=instance
                Log.d("TAG", "getDatabase Instance created: $INSTANCE")

                return instance
            }
        }
    }
}