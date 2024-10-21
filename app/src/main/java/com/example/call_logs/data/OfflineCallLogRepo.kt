package com.example.call_logs.data

import com.example.call_logs.data.model.CallLogTable
import kotlinx.coroutines.flow.Flow

class OfflineCallLogRepo(private val callLogDao: CallLogDAO): CallLogRepo {

    override suspend fun insertCallLog(callLog: CallLogTable) {
        callLogDao.insert(callLog)
    }

    override fun selectCallLog(): Flow<List<CallLogTable>> {
        return callLogDao.select()
    }

}