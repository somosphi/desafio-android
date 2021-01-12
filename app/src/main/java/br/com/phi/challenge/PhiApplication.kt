package br.com.phi.challenge

import android.app.Application
import br.com.phi.challenge.di.repositoryModule
import br.com.phi.challenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by pcamilo on 09/01/2021.
 */
class PhiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PhiApplication)
            modules(listOf(viewModelModule, repositoryModule))
        }
    }
}