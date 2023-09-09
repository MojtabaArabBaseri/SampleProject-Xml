package ir.millennium.sampleProject.domain.repository.local

import ir.millennium.sampleProject.data.model.local.formUnregistered.NewsModel

interface LocalRepository {

    suspend fun getNewsList(): List<NewsModel>

    suspend fun saveToDatabaseNews(newsModel: NewsModel)

    fun clearDatabase()

}