package com.henrique.desafio_android.domain.home

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.domain.error.ErrorHandler
import com.henrique.desafio_android.network.Repository
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList
import com.henrique.desafio_android.presenter.model.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GetMyStatementInteractor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val resources: Resources,
    private val errorHandler: ErrorHandler = ErrorHandler(resources = resources)
) {
    val requestStateDetail = MutableLiveData<RequestState<MyStatementResponse>>(RequestState.Idle)
    val requestState = MutableLiveData<RequestState<MyStatementResponseList>>(RequestState.Idle)

    fun getMyStatement(limit: String, offset: String) {
        requestState.value = RequestState.Loading

        coroutineScope.launch {
            try {
                val response = repository.getMyStatement(limit, offset)

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

    fun getStatementDetail(id: String) {
        requestStateDetail.value = RequestState.Loading

        coroutineScope.launch {
            try {
                val response = repository.getStatementDetail(id)

                requestStateDetail.postValue(RequestState.Success(response))
            } catch (e: Exception) {
                requestStateDetail.postValue(
                    RequestState.Error(
                        errorHandler.buildErrorResponse(e)
                    )
                )
            }
        }
    }

}