package com.henrique.desafio_android.domain.repository

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.domain.model.error.ErrorHandler
import com.henrique.desafio_android.domain.model.balance.BalanceResponse
import com.henrique.desafio_android.domain.model.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GetBalanceInteractor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val resources: Resources,
    private val errorHandler: ErrorHandler = ErrorHandler(resources = resources)
) {
    val requestState = MutableLiveData<RequestState<BalanceResponse>>(RequestState.Idle)

    fun getBalance() {
        requestState.postValue(RequestState.Loading)

        coroutineScope.launch {
            try {
                val response = repository.getBalance()

                requestState.postValue(RequestState.Success(response))
            } catch (e: Exception) {
                requestState.postValue(
                    RequestState.Error(
                        errorHandler.buildErrorResponse(e)
                    )
                )
            }
        }
    }
}