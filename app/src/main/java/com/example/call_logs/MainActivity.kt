package com.example.call_logs

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.call_logs.ui.Navigation
import com.example.call_logs.ui.TopBar
import com.example.call_logs.ui.theme.CalllogsTheme
import android.Manifest
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
  //  private val apiViewModel: UrlApiViewModel by viewModel()
  //  private val dataStoreViewModel: UrlDataStoreViewModel by viewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CalllogsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {TopBar(navController)}
                    )
                {
                    Navigation(navController = navController)
                }
            }
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                scheduleCallLogWorker() // Schedule worker if permission is granted
            } else {
                // Handle permission denied case
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        checkPermissions()
    }

    /*
    private fun readCallLogs() {
        lifecycleScope.launch {
            val cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
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

                   val callLog = com.example.call_logs.data.model.CallLog(
                        mobileNumber = phoneNumber,
                        callType = callTypeString,
                        callDuration = callDuration,
                        callDate = callDate.toString()
                   )
                    apiViewModel.sendCallLogs(callLog, dataStoreViewModel.accessUrl().toString())
                }
            }
        }
    }
*/
    private fun scheduleCallLogWorker() {
        val workRequest = PeriodicWorkRequestBuilder<CallLogWorker>(12, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // Only run when connected to the internet
                    .setRequiresBatteryNotLow(true) // Optional: run only when battery is not low
                    .build()
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "CallLogWorker",
            ExistingPeriodicWorkPolicy.KEEP, // Prevent creating multiple workers
            workRequest
        )
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            requestPermissionLauncher.launch(Manifest.permission.READ_CALL_LOG)
        } else {
            // Permission is already granted, schedule the worker
            scheduleCallLogWorker()
        }
    }
}

