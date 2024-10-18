package com.example.call_logs.viewmodel

import androidx.lifecycle.ViewModel
import com.example.call_logs.data.callApi
import com.example.call_logs.data.model.CallLog
import com.example.call_logs.data.model.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface UrlApiStatus{
    data class Success(val status: Response): UrlApiStatus
    object Error: UrlApiStatus
    object Loading: UrlApiStatus
}

class UrlApiViewModel: ViewModel() {

    private val status = MutableStateFlow<UrlApiStatus>(UrlApiStatus.Loading)
    val _status: StateFlow<UrlApiStatus> = status

    suspend fun sendCallLogs(callLog: CallLog){
        status.value = UrlApiStatus.Loading
        status.value = try{
        val response = callApi.retrofitService.sendCallLogs(callLog)
            UrlApiStatus.Success(response)
        }
        catch(e: Exception){
            e.printStackTrace()
            UrlApiStatus.Error
        }
    }
}