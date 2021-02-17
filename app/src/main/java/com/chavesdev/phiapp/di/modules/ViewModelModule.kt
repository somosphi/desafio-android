package com.chavesdev.phiapp.di.modules

import android.app.Application
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.repo.AccountRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(accountRepo: AccountRepo, application: Application) = ViewModelFactory(accountRepo, application)
}