package com.chavesdev.phiapp.di.modules

import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.repo.AccountRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(accountRepo: AccountRepo) = ViewModelFactory(accountRepo)
}