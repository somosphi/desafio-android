package com.chavesdev.phiapp.di.modules

import android.app.Application
import com.chavesdev.phiapp.PhiAppApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PhiAppModule(private val application: PhiAppApplication) {

    @Provides
    @Singleton
    fun providePhiAppApplication(application: PhiAppApplication): PhiAppApplication = application

    @Provides
    fun provideApplication(): Application = application
}