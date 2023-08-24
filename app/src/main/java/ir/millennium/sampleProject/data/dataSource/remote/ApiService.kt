package ir.millennium.sampleProject.data.dataSource.remote

import ir.millennium.sampleProject.data.model.remote.ResponseNewsModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("everything")
    suspend fun getNews(@QueryMap parameters: MutableMap<String, Any>): ResponseNewsModel

}