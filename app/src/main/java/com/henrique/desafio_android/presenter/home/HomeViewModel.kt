package com.henrique.desafio_android.presenter.home

import android.content.res.Resources
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.utils.formatCurrency
import com.henrique.desafio_android.domain.home.GetBalanceInteractor
import com.henrique.desafio_android.presenter.model.BaseViewModel
import com.henrique.desafio_android.presenter.model.RequestState
import java.math.BigDecimal

class HomeViewModel(
    private val balanceInteractor: GetBalanceInteractor,
    private val resources: Resources
) : BaseViewModel() {

    val isBalanceVisible = MutableLiveData(true)
    private val balanceAmount = MutableLiveData<BigDecimal>()
    val balanceAmountText = MediatorLiveData<String>()

    init {
        balanceAmountText.addSource(balanceAmount) {
            balanceAmountText.postValue(
                balanceAmount.value?.formatCurrency()
            )
        }

        requestState.addSource(balanceInteractor.requestState) {
            requestState.value = it

            when (it) {
                is RequestState.Success -> {
                    balanceAmount.postValue(it.result.amount)
                }
                else -> { /* Intentionally left empty */
                }
            }
        }
    }

    fun toggleBalanceVisibility() {
        isBalanceVisible.value = isBalanceVisible.value?.not()
    }

    private fun getBalance() {
        balanceInteractor.getBalance()
    }

    fun resume() {
        getBalance()
    }

}