package com.example.call_logs.viewmodel

import androidx.lifecycle.ViewModel
import com.example.call_logs.data.callApi
import com.example.call_logs.data.model.CallLog

sealed interface UrlApiStatus{
    data class Success(val url: String): UrlApiStatus
    object Error: UrlApiStatus
    object Loading: UrlApiStatus
}

class UrlApiViewModel: ViewModel() {

    suspend fun sendCallLogs(callLog: CallLog){

        callApi.retrofitService.sendCallLogs(callLog)
    }
}