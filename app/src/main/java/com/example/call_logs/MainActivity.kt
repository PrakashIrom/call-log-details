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
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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
                Log.d("MainActivity", "Permission not granted, requesting permission")
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        checkPermissions()
    }

    private fun scheduleCallLogWorker() {
        val workRequest = OneTimeWorkRequestBuilder<CallLogWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)

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

