package com.ihsan.news_app.roomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihsan.news_app.model.Article
import com.ihsan.news_app.roomdb.dao.NewsDao

@Database(entities = [Article::class], version=5, exportSchema = false)
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
                return  instance
            }
        }
    }
}