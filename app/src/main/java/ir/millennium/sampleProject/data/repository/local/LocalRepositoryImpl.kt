package ir.millennium.sampleProject.data.repository.local

import ir.millennium.sampleProject.data.dataSource.local.database.RoomServiceDao
import ir.millennium.sampleProject.data.mapper.mapToEntity
import ir.millennium.sampleProject.data.mapper.mapToUiModelList
import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredModel
import ir.millennium.sampleProject.domain.repository.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var database: RoomServiceDao,
    private val applicationScope: CoroutineScope
) : LocalRepository {
    override suspend fun getFormUnRegistredList(): List<FormUnRegisteredModel> {
        return database.getAndroidVersions().mapToUiModelList()
    }

    override suspend fun saveToDatabaseFormUnRegistered(formUnRegisteredModel: FormUnRegisteredModel) {
        return withContext(applicationScope.coroutineContext) {
            database.insert(formUnRegisteredModel.mapToEntity())
        }
    }

    override fun clearDatabase() {
        applicationScope.launch {
            database.clear()
        }
    }
}