package com.chavesdev.phiapp.di.modules.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.views.main.BalanceViewModel
import com.chavesdev.phiapp.views.main.StatementsViewModel
import com.chavesdev.phiapp.views.statement_details.StatementDetailViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(val accountRepo: AccountRepo, val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BalanceViewModel::class.java)) {
            return BalanceViewModel(accountRepo, application) as T
        }
        if(modelClass.isAssignableFrom(StatementsViewModel::class.java)) {
            return StatementsViewModel(accountRepo) as T
        }
        if(modelClass.isAssignableFrom(StatementDetailViewModel::class.java)) {
            return StatementDetailViewModel(accountRepo) as T
        }
        throw IllegalArgumentException("Invalid ViewModel")
    }
}