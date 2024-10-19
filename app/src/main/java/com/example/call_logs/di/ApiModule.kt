package com.example.call_logs.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.call_logs.CallLogWorker
import com.example.call_logs.data.UrlDataStore
import com.example.call_logs.viewmodel.UrlApiViewModel
import com.example.call_logs.viewmodel.UrlDataStoreViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("call_logs_preferences")

val urlModule = module{
        single{
               UrlDataStore(provideDataStore(get()))
        }
    viewModel<UrlDataStoreViewModel> {
        UrlDataStoreViewModel(get())
    }
    viewModel<UrlApiViewModel>{
        UrlApiViewModel()
    }
    single{
        CallLogWorker(get(), get())
    }
}

fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
}
