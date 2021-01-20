package com.ipsoft.ph.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.repository.model.Balance
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.repository.model.TransactionResponse

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

class MainViewModel(private val repository: HttpRepository) : ViewModel() {

    var transactionsLiveData = MutableLiveData<TransactionResponse>()
    var balanceLiveData = MutableLiveData<Balance>()
    var detailsLiveData = MutableLiveData<Transaction>()

    fun getBalance(): LiveData<Balance> {

        balanceLiveData = repository.getBalance()
        return balanceLiveData
    }

    fun getTransactions() : LiveData<TransactionResponse> {

        transactionsLiveData = repository.getTransactions()
        return transactionsLiveData
    }

    fun getDetailTransaction(id: String) : LiveData<Transaction> {

        detailsLiveData = repository.getDetailTransaction(id)
        return detailsLiveData

    }


    class StatementViewModelFactory(private val repository: HttpRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }

    }
}