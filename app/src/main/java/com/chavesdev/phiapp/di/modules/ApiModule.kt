package com.chavesdev.phiapp.di.modules

import com.chavesdev.phiapp.repo.api.AccountApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)
}