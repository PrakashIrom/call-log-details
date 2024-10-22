package com.example.call_logs.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.call_logs.ui.theme.CalllogsTheme
import com.example.call_logs.viewmodel.CallLogRoomViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CallLogScreen(roomDb:CallLogRoomViewModel = koinViewModel()){

    var search by remember{
        mutableStateOf("")
    }
    val callLogItems = roomDb.items.collectAsState().value

    Column {

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }, modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                .fillMaxWidth()
        )

        Text("")

        val searchItems = callLogItems.filter{
            it.mobileNumber.contains(search)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchItems) { item ->

                val (date, time) = item.callDate.split(" ").map { it.trim()}

                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceTint)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        var clicked by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(modifier = Modifier.weight(1f)) {
                                Icon(imageVector = Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = when(item.callType){
                                        "OUTGOING" -> MaterialTheme.colorScheme.inversePrimary
                                        "INCOMING" -> Color.Red
                                        else -> Color.Blue
                                    }
                                    )
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    text = item.mobileNumber,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(time)
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand/Collapse",
                                modifier = Modifier
                                    .clickable {
                                        clicked = !clicked
                                    }
                            )
                        }
                        if (clicked) {
                            Spacer(modifier = Modifier.size(13.dp))
                            Text(text = "Call type: ${item.callType}", fontWeight = FontWeight.Bold)
                            Text(text = "Duration: ${item.callDuration} secs", fontWeight = FontWeight.Bold)
                            Text(text = "Date: $date", fontWeight = FontWeight.Bold)
                        }
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