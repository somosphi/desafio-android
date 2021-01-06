package com.example.desafiophi

import android.app.Application
import com.example.desafiophi.architecture.di.phiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(phiModule))
        }
    }
}