package com.ihsan.news_app.roomdb.data

import com.ihsan.news_app.model.Article
import com.ihsan.news_app.model.NewsTable
import com.ihsan.news_app.model.Source

class DataConverter {
    fun getArticle(newses: List<NewsTable>): List<Article> {
        return newses.map{news->
            Article(
                news.author,
                news.content,
                news.description,
                news.publishedAt,
                Source(news.sourceId, news.sourceName),
                news.title,
                news.url,
                news.urlToImage
            )
        }
    }

    fun getNewsTable(newses: List<Article>?, category:String): List<NewsTable> {
        return newses!!.map{news->
            NewsTable(
                0,
                news.author,
                news.content,
                news.description,
                news.publishedAt,
                news.source?.id,
                news.source?.name,
                news.title,
                news.url,
                news.urlToImage,
                category,
                false
            )
        }
    }
}