package com.example.taskplanner

import android.app.Application
import com.example.taskplanner.di.firebaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                firebaseModule
            ))
        }
    }
}