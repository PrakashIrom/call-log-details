package com.example.call_logs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Call_Log")
data class CallLogTable(
    @PrimaryKey(autoGenerate = true)
    val mobileNumber: String,
    val callType: String,
    val callDuration: String,
    val callDate: String
)