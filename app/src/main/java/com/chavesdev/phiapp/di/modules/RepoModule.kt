package com.chavesdev.phiapp.di.modules

import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.repo.api.AccountApi
import dagger.Module
import dagger.Provides

@Module
class RepoModule {

    @Provides
    fun provideAccountRepo(accountApi: AccountApi) = AccountRepo(accountApi)
}