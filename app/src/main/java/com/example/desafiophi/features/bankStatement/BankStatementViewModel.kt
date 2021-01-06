package com.example.desafiophi.features.bankStatement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiophi.architecture.networking.Resource
import com.example.desafiophi.data.PhiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BankStatementViewModel : ViewModel() {

    private val service = PhiService()
    private val _balance = MutableLiveData<Resource<String>>()

    val balance: LiveData<Resource<String>>
        get() = _balance

    fun getBalance() {
        _balance.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            val res = service.getBalance()

            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _balance.value = Resource.success(res.body()?.amount?.toString()!!)
                }
            }
        }
    }
}