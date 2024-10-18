package com.example.call_logs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.call_logs.data.model.CallLog
import com.example.call_logs.ui.theme.CalllogsTheme

@Composable
fun CallLogScreen(){

    val items = listOf(CallLog("Incoming", "2:00", "12/12/2023", "1234567890"),
        CallLog("Outgoing", "3:40", "12/12/2024", "1234567890"),
        CallLog("Missed Call", "12:21", "12/12/2024", "1234567890"),
        CallLog("Incoming", "9:30", "12/12/2023", "1234567890")
        )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(top=40.dp)){
        items(items){item->
            Card(modifier = Modifier.padding(10.dp).fillMaxWidth(),
                colors = when(item.mobileNumber){
                    "Incoming" -> CardDefaults.cardColors(Color.DarkGray)
                    "Outgoing" -> CardDefaults.cardColors(Color.Green)
                    else -> CardDefaults.cardColors(Color.Red)
                }
            ){
                Column(modifier = Modifier.padding(10.dp)){
                    Text(text = "Call duration: ${item.callType}", fontWeight = FontWeight.Bold)
                    Text(text = "Date: ${item.callDuration}", fontWeight = FontWeight.Bold)
                    Text(text = "Number: ${item.callDate}", fontWeight = FontWeight.Bold)
                    Text(text = "Call type: ${item.mobileNumber}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewCallLog(){
    CalllogsTheme{
        CallLogScreen()
    }
}