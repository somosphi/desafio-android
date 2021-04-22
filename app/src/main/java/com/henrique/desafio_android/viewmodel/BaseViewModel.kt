package com.henrique.desafio_android.viewmodel

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.henrique.desafio_android.domain.model.RequestState
import com.henrique.desafio_android.view.component.MyLoader

open class BaseViewModel : ViewModel() {

    val requestState = MediatorLiveData<RequestState<Any>>()

    val contentVisibility = MediatorLiveData<Int>().apply {
        addSource(requestState) {
            value = when (it) {
                is RequestState.Success -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    val loaderState = MediatorLiveData<MyLoader.State>().apply {
        addSource(requestState) {
            value = when (it) {
                is RequestState.Error -> MyLoader.State.ERROR
                RequestState.Loading, RequestState.Idle -> MyLoader.State.LOADING
                is RequestState.Success -> MyLoader.State.COMPLETE
            }
        }
    }

}