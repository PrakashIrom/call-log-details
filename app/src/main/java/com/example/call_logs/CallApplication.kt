package com.example.call_logs

import android.app.Application
import com.example.call_logs.di.roomModule
import com.example.call_logs.di.urlModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CallApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@CallApplication)
            modules(urlModule, roomModule)
        }
    }
}