package com.example.call_logs.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.CONFIGURATION.name){
        composable(Screens.CONFIGURATION.name){
            ConfigurationScreen(navController)
        }
        composable(Screens.CALL_LOGS.name){
            CallLogScreen()
        }
    }
}