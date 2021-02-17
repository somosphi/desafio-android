package com.chavesdev.phiapp.di

import android.app.Application
import com.chavesdev.phiapp.di.modules.*
import com.chavesdev.phiapp.views.BalanceAndStatementsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PhiAppModule::class,
        RetrofitModule::class,
        ApiModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface PhiAppComponent {
    fun inject(balanceAndStatementsActivity: BalanceAndStatementsActivity)
    fun application(): Application
}