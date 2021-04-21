package com.henrique.desafio_android.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.service.utils.formatCurrency
import com.henrique.desafio_android.service.repository.GetBalanceInteractor
import com.henrique.desafio_android.service.repository.GetMovimentationInteractor
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponseList
import com.henrique.desafio_android.service.model.RequestState
import java.math.BigDecimal

class HomeViewModel(
    private val balanceInteractor: GetBalanceInteractor,
    private val movimentationInteractor: GetMovimentationInteractor
) : BaseViewModel() {

    var offset = 0
    private val limit = 10
    private val balanceAmount = MutableLiveData<BigDecimal>()
    val isBalanceVisible = MutableLiveData(true)
    val balanceAmountText = MediatorLiveData<String>()
    val myMovimentationResponse = MutableLiveData<MovimentationResponseList>()

    init {
        balanceAmountText.addSource(balanceAmount) {
            balanceAmountText.postValue(
                balanceAmount.value?.formatCurrency()
            )
        }

        observeGetBalance()
        observeGetMovimentation()
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

    private fun observeGetMovimentation() {
        requestState.addSource(movimentationInteractor.requestState) {
            requestState.value = it

            when (it) {
                is RequestState.Success -> {
                    it.result.let { response ->
                        myMovimentationResponse.postValue(response)
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

    private fun getBalance() {
        balanceInteractor.getBalance()
    }

    fun getMovimentation() {
        if (movimentationInteractor.requestState.value != RequestState.Loading) {
            movimentationInteractor.getMovimentation(limit.toString(), offset.toString())
        }
    }

    fun resume() {
        getBalance()
        getMovimentation()
    }

}