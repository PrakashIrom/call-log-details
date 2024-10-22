package com.example.call_logs

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class CallReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)

        if (state == TelephonyManager.EXTRA_STATE_IDLE) {
            // Phone call has ended, enqueue work to sync call logs
            if (context != null) {
                val workRequest = OneTimeWorkRequestBuilder<CallLogWorker>()
                    .build()
                WorkManager.getInstance(context).enqueue(workRequest)
                Log.d("CallReceiver", "Call log sync work enqueued.")
            }
        }
    }
}
