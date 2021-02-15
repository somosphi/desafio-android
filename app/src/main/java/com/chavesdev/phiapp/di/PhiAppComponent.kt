package com.chavesdev.phiapp.di

import android.app.Application
import com.chavesdev.phiapp.di.modules.PhiAppModule
import com.chavesdev.phiapp.di.modules.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PhiAppModule::class,
        RetrofitModule::class,
    ]
)
interface PhiAppComponent {
    fun application(): Application
}