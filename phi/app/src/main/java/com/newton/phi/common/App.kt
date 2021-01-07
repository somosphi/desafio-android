package com.newton.phi.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@App)
            koin.loadModules(listOf())
            koin.createRootScope()
        }
    }

}