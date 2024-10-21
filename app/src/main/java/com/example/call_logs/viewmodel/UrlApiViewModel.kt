package com.example.call_logs.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.call_logs.data.ApiCallLog
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
    val callLogs = mutableStateListOf<CallLog>()

    @SuppressLint("SuspiciousIndentation")
    suspend fun sendCallLogs(callLog: CallLog, baseUrl:String){
        Log.d("Base url", baseUrl)
        status.value = UrlApiStatus.Loading
        status.value = try{
            val callApi = ApiCallLog(baseUrl)
            val response = callApi.retrofitService.sendCallLogs(callLog)
            UrlApiStatus.Success(response)
        }
        catch(e: Exception){
            e.printStackTrace()
            UrlApiStatus.Error
        }
    }

    fun addCallLog(callLog:CallLog){
        Log.d("CallLogViewModel", "Adding call log: ${callLog.mobileNumber}, ${callLog.callType}, ${callLog.callDuration}, ${callLog.callDate}")
        callLogs.add(callLog)
    }

}