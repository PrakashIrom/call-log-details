package com.example.call_logs.di

import com.example.call_logs.data.CallLogDatabase
import com.example.call_logs.data.OfflineCallLogRepo
import com.example.call_logs.viewmodel.CallLogRoomViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel

val roomModule = module{
    single{
    OfflineCallLogRepo(CallLogDatabase.getDatabase(androidContext()).callLogDao())
    }
    viewModel{
        CallLogRoomViewModel(get())
    }
}