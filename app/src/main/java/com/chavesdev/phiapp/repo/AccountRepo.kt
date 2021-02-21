package com.chavesdev.phiapp.repo

import androidx.lifecycle.ViewModel
import com.chavesdev.phiapp.repo.api.AccountApi

class AccountRepo(private val accountApi: AccountApi) : ViewModel() {

    suspend fun getBalance() = accountApi.getBalance()

    suspend fun getStatements(perPage : Int, page: Int)  = accountApi.getStatements(perPage, page)

    suspend fun getStatementDetails(id: String) = accountApi.getStatementDetail(id)
}