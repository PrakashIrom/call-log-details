package com.example.call_logs.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CallLog(
    val mobileNumber: String,
    val callType: String,
    val callDuration: String,
    val callDate: String
)

@Serializable
data class Response(
    val status : String
)
