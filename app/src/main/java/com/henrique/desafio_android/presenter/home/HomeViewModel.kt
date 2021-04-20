package com.henrique.desafio_android.presenter.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.utils.formatCurrency
import com.henrique.desafio_android.domain.home.GetBalanceInteractor
import com.henrique.desafio_android.domain.home.GetMyStatementInteractor
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList
import com.henrique.desafio_android.presenter.model.BaseViewModel
import com.henrique.desafio_android.presenter.model.RequestState
import java.math.BigDecimal

class HomeViewModel(
    private val balanceInteractor: GetBalanceInteractor,
    private val myStatementInteractor: GetMyStatementInteractor
) : BaseViewModel() {

    var offset = 0

    private val limit = 10
    private val balanceAmount = MutableLiveData<BigDecimal>()
    val isBalanceVisible = MutableLiveData(true)
    val balanceAmountText = MediatorLiveData<String>()
    val myStatementResponse = MutableLiveData<MyStatementResponseList>()

    init {
        balanceAmountText.addSource(balanceAmount) {
            balanceAmountText.postValue(
                balanceAmount.value?.formatCurrency()
            )
        }

        observeGetBalance()
        observeGetMyStatements()
    }

    private fun observeGetBalance() {
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

    private fun observeGetMyStatements() {
        requestState.addSource(myStatementInteractor.requestState) {
            requestState.value = it

            when (it) {
                is RequestState.Success -> {
                    it.result.let { response ->
                        myStatementResponse.postValue(response)
                    }
                }
                else -> { /* Intentionally left empty */
                }
            }
        }
    }

    fun toggleBalanceVisibility() {
        isBalanceVisible.value = isBalanceVisible.value?.not()
    }

    fun getBalance() {
        balanceInteractor.getBalance()
    }

    fun getMyStatement() {
        myStatementInteractor.getMyStatement(limit.toString(), offset.toString())
        offset++
    }

}