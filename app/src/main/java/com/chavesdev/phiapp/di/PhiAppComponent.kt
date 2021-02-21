package com.chavesdev.phiapp.di

import android.app.Application
import com.chavesdev.phiapp.di.modules.*
import com.chavesdev.phiapp.views.main.MainActivity
import com.chavesdev.phiapp.views.statement_details.StatementDetailsActivity
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
    fun inject(mainActivity: MainActivity)
    fun inject(statementDetailsActivity: StatementDetailsActivity)
    fun application(): Application
}