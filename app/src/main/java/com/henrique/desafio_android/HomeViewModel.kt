package com.henrique.desafio_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val isBalanceVisible = MutableLiveData(false)
    val balanceAmount = MutableLiveData("R$ 1.345,00")

    fun toggleBalanceVisibility() {
        isBalanceVisible.value = isBalanceVisible.value?.not()
    }

}