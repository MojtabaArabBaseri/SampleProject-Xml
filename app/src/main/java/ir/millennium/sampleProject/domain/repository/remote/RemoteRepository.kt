package ir.millennium.sampleProject.domain.repository.remote

import ir.millennium.sampleProject.data.model.remote.ResponseNewsModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getNews(params: MutableMap<String, Any>): Flow<ResponseNewsModel>

}