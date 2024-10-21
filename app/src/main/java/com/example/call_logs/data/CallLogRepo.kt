package com.example.call_logs.data

import com.example.call_logs.data.model.CallLogTable
import kotlinx.coroutines.flow.Flow

interface CallLogRepo {

    suspend fun insertCallLog(callLog: CallLogTable)

    fun selectCallLog(): Flow<List<CallLogTable>>

}