package com.example.call_logs.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.call_logs.data.UrlDataStore
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("call_logs_preferences")

val urlModule = module{
        single{
               UrlDataStore(provideDataStore(get()))
        }
}

fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
}
