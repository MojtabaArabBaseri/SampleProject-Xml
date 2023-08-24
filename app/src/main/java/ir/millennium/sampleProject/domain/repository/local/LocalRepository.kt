package ir.millennium.sampleProject.domain.repository.local

import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredModel

interface LocalRepository {

    suspend fun getFormUnRegistredList(): List<FormUnRegisteredModel>

    suspend fun saveToDatabaseFormUnRegistered(formUnRegisteredModel: FormUnRegisteredModel)

    fun clearDatabase()

}