package com.example.pedroneryphi.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(networkModules)
            modules(persistenceModules)
            modules(repositoryModule)
            modules(viewModelModules)
        }

    }

}