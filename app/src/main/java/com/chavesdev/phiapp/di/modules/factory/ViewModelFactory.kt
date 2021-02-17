package com.chavesdev.phiapp.di.modules.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.views.BalanceViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(val accountRepo: AccountRepo, val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BalanceViewModel::class.java)) {
            return BalanceViewModel(accountRepo, application) as T
        }
        throw IllegalArgumentException("Invalid ViewModel")
    }
}