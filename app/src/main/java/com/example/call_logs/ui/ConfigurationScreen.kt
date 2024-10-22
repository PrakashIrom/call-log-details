package com.example.call_logs.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.call_logs.ui.theme.CalllogsTheme
import com.example.call_logs.viewmodel.UrlDataStoreViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConfigurationScreen(navController: NavHostController, viewModel: UrlDataStoreViewModel = koinViewModel()){

    var input by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ){
        OutlinedTextField(value = input,
            onValueChange = {input = it},
            shape = RoundedCornerShape(10.dp),
            label = { Text("Enter Url", color = MaterialTheme.colorScheme.onBackground) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedButton(onClick = {
            coroutineScope.launch {
                viewModel.saveUrl(input)
            }
            input=""
            Toast.makeText(context, "Url Saved", Toast.LENGTH_SHORT).show()
        },
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.inversePrimary),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.2.dp)
            ) {
            Text("Save Url")
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = {
            navController.navigate(Screens.CALL_LOGS.name)
        },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.inverseSurface
            )
            ) {
            Text("Go to Call Logs")
            Spacer(modifier = Modifier.size(10.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfiguration(){
    CalllogsTheme {
        val navController = rememberNavController()
        ConfigurationScreen(navController = navController)
    }
}