package com.newton.phi.common

import android.app.Application
import com.newton.phi.di.moduleInicializerRequest
import com.newton.phi.di.moduleInterector
import com.newton.phi.di.moduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@App)
            koin.loadModules(listOf(moduleViewModel, moduleInicializerRequest, moduleInterector))
            koin.createRootScope()
        }
    }

}