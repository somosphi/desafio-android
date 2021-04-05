package com.example.phitest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phitest.model.Transaction
import com.example.phitest.model.User
import com.example.phitest.model.TransactionDetail
import com.example.phitest.repository.TransactionDetailRepository

class TransactionDetailViewModel(private val repository: TransactionDetailRepository) : ViewModel() {

    fun getDetailById(id: String) : LiveData<Result<TransactionDetail?>> =
        repository.getDetailById(id)

    fun myBalance(id: String) : LiveData<Result<User?>> =
        repository.myBalance(id)

    fun myStatements(token: String, limit: Int, offset: Int) : LiveData<Result<Transaction?>> =
        repository.myStatements(token,limit, offset)
}