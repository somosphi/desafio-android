package com.chavesdev.phiapp

import android.app.Application
import com.chavesdev.phiapp.di.DaggerPhiAppComponent
import com.chavesdev.phiapp.di.PhiAppComponent
import com.chavesdev.phiapp.di.modules.PhiAppModule
import com.chavesdev.phiapp.di.modules.RetrofitModule

class PhiAppApplication : Application() {

    lateinit var phiAppComponent: PhiAppComponent

    override fun onCreate() {
        super.onCreate()

        phiAppComponent = DaggerPhiAppComponent.builder()
            .phiAppModule(PhiAppModule(this))
            .retrofitModule(RetrofitModule())
            .build()
    }
}