package com.ipsoft.ph.viewmodel

import androidx.lifecycle.LiveData
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



    fun getBalance(): LiveData<Balance> {


        return repository.getBalance()
    }

    fun getTransactions(): LiveData<TransactionResponse> {


        return repository.getTransactions()
    }

    fun getDetailTransaction(id: String): LiveData<Transaction> {


        return repository.getDetailTransaction(id)

    }


    class MainViewModelFactory(private val repository: HttpRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }

    }
}




