package com.example.call_logs.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

class UrlDataStore(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    private val urlKey = stringPreferencesKey("urlKey")

    val accessUrl: Flow<String> = dataStore.data.map{
        preferences ->
        preferences[urlKey] ?: ""
    }

    suspend fun saveUrl(value: String){
        dataStore.edit{
            preferences->
            preferences[urlKey] = value
        }
    }

}