package ir.millennium.sampleProject.data.repository.local

import ir.millennium.sampleProject.data.dataSource.local.database.RoomServiceDao
import ir.millennium.sampleProject.data.mapper.mapToEntity
import ir.millennium.sampleProject.data.mapper.mapToUiModelList
import ir.millennium.sampleProject.data.model.local.formUnregistered.NewsModel
import ir.millennium.sampleProject.domain.repository.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var database: RoomServiceDao,
    private val applicationScope: CoroutineScope
) : LocalRepository {
    override suspend fun getNewsList(): List<NewsModel> {
        return database.getAndroidVersions().mapToUiModelList()
    }

    override suspend fun saveToDatabaseNews(newsModel: NewsModel) {
        return withContext(applicationScope.coroutineContext) {
            database.insert(newsModel.mapToEntity())
        }
    }

    override fun clearDatabase() {
        applicationScope.launch {
            database.clear()
        }
    }
}