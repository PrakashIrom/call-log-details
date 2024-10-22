package com.example.call_logs.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.call_logs.ui.theme.CalllogsTheme

@Composable
fun TopBar(navController: NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route

    if (currentScreen == Screens.CONFIGURATION.name) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.TopCenter).padding(7.dp)) {
                Text("Configuration",
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.size(5.dp))
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Configuration")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Row(modifier = Modifier.align(Alignment.TopCenter).padding(10.dp)) {
                Text("Call Logs",
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.size(5.dp))
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    CalllogsTheme {
        val navController = rememberNavController()
        TopBar(navController)
    }
}
