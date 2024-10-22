package com.example.call_logs

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.call_logs.ui.Navigation
import com.example.call_logs.ui.TopBar
import com.example.call_logs.ui.theme.CalllogsTheme
import android.Manifest
import android.content.IntentFilter
import android.telephony.TelephonyManager
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding


class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request permissions
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.READ_CALL_LOG] == true &&
                permissions[Manifest.permission.READ_PHONE_STATE] == true) {
                // Permissions granted, proceed
                registerCallReceiver()
                Log.d("MainActivity", "Permissions granted.")
            } else {
                // Handle permission denied case
                Log.d("MainActivity", "Permissions not granted.")
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }

        checkPermissions()

        setContent {
            val navController = rememberNavController()
            enableEdgeToEdge()
            CalllogsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                    topBar = { TopBar(navController) }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    private fun checkPermissions() {
        val permissionsNeeded = arrayOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE
        )

        val permissionsGranted = permissionsNeeded.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!permissionsGranted) {
            requestPermissionLauncher.launch(permissionsNeeded)
        } else {
            // Permissions are already granted, you can initialize things like WorkManager
            Log.d("MainActivity", "All permissions are already granted.")
        }
    }

    private fun registerCallReceiver() {
        // Register CallReceiver for PHONE_STATE changes
        val intentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(CallReceiver(), intentFilter)
        Log.d("MainActivity", "CallReceiver registered.")
    }
}
