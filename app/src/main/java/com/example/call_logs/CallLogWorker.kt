package com.example.call_logs

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.call_logs.viewmodel.UrlApiViewModel
import com.example.call_logs.viewmodel.UrlDataStoreViewModel
import android.provider.CallLog
import android.util.Log
import com.example.call_logs.data.model.CallLogTable
import com.example.call_logs.viewmodel.CallLogRoomViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.Date

class CallLogWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val apiViewModel: UrlApiViewModel by inject(UrlApiViewModel::class.java)
    private val dataStoreViewModel: UrlDataStoreViewModel by inject(UrlDataStoreViewModel::class.java)
    private val roomViewModel: CallLogRoomViewModel by inject(CallLogRoomViewModel::class.java)

    override suspend fun doWork(): Result {
        return try {
            syncCallLogs(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Log.e("CallLogSyncWorker", "Error syncing call logs", e)
            Result.failure()
        }
    }

    private suspend fun syncCallLogs(context: Context) {
        val projection = arrayOf(
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )

        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            null,
            null,
            CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)

            while (it.moveToNext()) {
                val phoneNumber = it.getString(numberIndex)
                val callType = it.getInt(typeIndex)
                val callDuration = it.getString(durationIndex)
                val callDate = it.getLong(dateIndex)
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(
                    Date(callDate)
                )
                val callTypeString = when (callType) {
                    CallLog.Calls.INCOMING_TYPE -> "INCOMING"
                    CallLog.Calls.OUTGOING_TYPE -> "OUTGOING"
                    CallLog.Calls.MISSED_TYPE -> "MISSED"
                    else -> "UNKNOWN"
                }

                // Collect data from DataStore
                val baseUrl = dataStoreViewModel.accessUrl().firstOrNull() ?: ""

                // Create the CallLog model object
                val callLog = com.example.call_logs.data.model.CallLog(
                    mobileNumber = phoneNumber,
                    callType = callTypeString,
                    callDuration = callDuration,
                    callDate = date.toString()
                )

                val roomCallLog = CallLogTable(
                    mobileNumber = phoneNumber,
                    callType = callTypeString,
                    callDuration = callDuration,
                    callDate = date.toString()
                )

                Log.d("CallLogSyncWorker", "Adding call log: $callLog")
                apiViewModel.sendCallLogs(callLog, baseUrl)
                roomViewModel.insertCallLog(roomCallLog)
               // apiViewModel.addCallLog(callLog)
            }
        }
    }
}
