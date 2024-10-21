package com.example.call_logs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.call_logs.data.CallLogRepo
import com.example.call_logs.data.model.CallLogTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CallLogRoomViewModel(private val repo: CallLogRepo): ViewModel() {

    private val _items = MutableStateFlow<List<CallLogTable>>(emptyList())
    val items: StateFlow<List<CallLogTable>> = _items

    suspend fun insertCallLog(callLog: CallLogTable) {
        repo.insertCallLog(callLog)
    }

    init{
        viewModelScope.launch {
            repo.selectCallLog().collect{
                items->
                _items.value = items
            }
        }
    }

}