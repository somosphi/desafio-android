package com.henrique.desafio_android.presenter.model

import com.henrique.desafio_android.network.response.ErrorResponse

sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Error(val error: ErrorResponse? = null) : RequestState<Nothing>()
    data class Success<T>(val result: T) : RequestState<T>()
}
