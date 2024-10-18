package com.example.call_logs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.call_logs.data.model.CallLog
import com.example.call_logs.ui.theme.CalllogsTheme

@Composable
fun CallLogScreen(){

    var search by remember{
        mutableStateOf("")
    }

    val items = listOf(CallLog("Incoming", "2:00", "12/12/2023", "7645"),
        CallLog("Outgoing", "3:40", "12/12/2024", "1119"),
        CallLog("Missed Call", "12:21", "12/12/2024", "9999"),
        CallLog("Incoming", "9:30", "12/12/2023", "6023")
        )

    Column {

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }, modifier = Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp, bottom = 18.dp).fillMaxWidth()
        )

        val searchItems = items.filter{
            it.callDate.contains(search)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchItems) { item ->
                Card(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    colors = when (item.mobileNumber) {
                        "Incoming" -> CardDefaults.cardColors(Color.DarkGray)
                        "Outgoing" -> CardDefaults.cardColors(Color.Green)
                        else -> CardDefaults.cardColors(Color.Red)
                    }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Call duration: ${item.callType}", fontWeight = FontWeight.Bold)
                        Text(text = "Date: ${item.callDuration}", fontWeight = FontWeight.Bold)
                        Text(text = "Number: ${item.callDate}", fontWeight = FontWeight.Bold)
                        Text(text = "Call type: ${item.mobileNumber}", fontWeight = FontWeight.Bold)
                    }
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