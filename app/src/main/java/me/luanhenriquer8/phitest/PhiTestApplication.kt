package me.luanhenriquer8.phitest

import android.app.Application
import me.luanhenriquer8.phitest.di.applicationModule
import me.luanhenriquer8.phitest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PhiTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PhiTestApplication)
            modules(
                listOf(applicationModule, viewModelModule)
            )
        }

    }
}