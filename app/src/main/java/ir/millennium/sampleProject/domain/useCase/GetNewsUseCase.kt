package ir.millennium.sampleProject.domain.useCase

import ir.millennium.sampleProject.data.model.remote.ResponseNewsModel
import ir.millennium.sampleProject.data.repository.remote.RemoteRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetNewsUseCase @Inject constructor(private val remoteRepository: RemoteRepositoryImpl) {

    open fun getNews(params: MutableMap<String, Any>): Flow<ResponseNewsModel> {
        return remoteRepository.getNews(params)
    }
}