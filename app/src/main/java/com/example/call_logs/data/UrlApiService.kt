package com.example.call_logs.data

import com.example.call_logs.data.model.CallLog
import com.example.call_logs.data.model.Response
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MediaType.Companion.toMediaType

class ApiCallLog(baseUrl:String) {
    interface UrlApiService {

        @POST("/syncCallLogs")
        suspend fun sendCallLogs(
            @Body callLog: CallLog
        ): Response

    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

        val retrofitService: UrlApiService by lazy {
            retrofit.create(UrlApiService::class.java)
        }

}