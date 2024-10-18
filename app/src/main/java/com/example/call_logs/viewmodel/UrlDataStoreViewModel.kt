package com.example.call_logs.viewmodel

import androidx.lifecycle.ViewModel
import com.example.call_logs.data.UrlDataStore
import kotlinx.coroutines.flow.Flow

class UrlDataStoreViewModel(private val urlDataStore: UrlDataStore): ViewModel() {

    fun accessUrl(): Flow<String> {
        return urlDataStore.accessUrl
    }

    suspend fun saveUrl(value: String){
        urlDataStore.saveUrl(value)
    }

}