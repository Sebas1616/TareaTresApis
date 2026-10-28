package com.example.tareatresapis.data.remote

import com.example.tareatresapis.data.remote.dto.SqliteQueryRequest
import com.example.tareatresapis.data.remote.dto.SqliteCloudResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SqliteCloudApiService {
    @POST("v1/json")
    suspend fun executeQuery(
        @Header("Authorization") bearerToken: String,
        @Body request: SqliteQueryRequest
    ): Response<SqliteCloudResponse>
}
