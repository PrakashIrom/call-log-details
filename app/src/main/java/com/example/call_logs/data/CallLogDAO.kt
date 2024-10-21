package com.example.call_logs.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.call_logs.data.model.CallLogTable
import kotlinx.coroutines.flow.Flow

interface CallLogDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(callLog: CallLogTable)

    @Query("SELECT * FROM Call_Log")
    fun select(): Flow<List<CallLogTable>>
}