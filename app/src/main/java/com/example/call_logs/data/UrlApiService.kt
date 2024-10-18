package com.example.call_logs.data

import com.example.call_logs.data.model.CallLog
import com.example.call_logs.data.model.Response
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL = "https://api.example.com"

interface UrlApiService{

    @POST("/syncCallLogs")
    suspend fun sendCallLogs(
        @Body callLog: CallLog
    ): Response

}

private val retrofit:Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

object callApi {
    val retrofitService: UrlApiService by lazy{
    retrofit.create(UrlApiService::class.java)
    }
}