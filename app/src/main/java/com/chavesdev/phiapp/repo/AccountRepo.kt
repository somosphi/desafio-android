package com.chavesdev.phiapp.repo

import androidx.lifecycle.ViewModel
import com.chavesdev.phiapp.repo.api.AccountApi

class AccountRepo(private val accountApi: AccountApi) : ViewModel() {

    suspend fun getBalance() = accountApi.getBalance()
}