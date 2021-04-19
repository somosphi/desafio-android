package com.henrique.desafio_android.presenter.model

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val requestState = MediatorLiveData<RequestState<Any>>()

    val isLoading = MediatorLiveData<Int>().apply {
        addSource(requestState) {
            value = when (it) {
                is RequestState.Loading -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    val errorVisibility = MediatorLiveData<Int>().apply {
        addSource(requestState) {
            value = when (it) {
                is RequestState.Error -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    val contentVisibility = MediatorLiveData<Int>().apply {
        addSource(requestState) {
            value = when (it) {
                is RequestState.Idle,
                is RequestState.Success -> View.VISIBLE
                else -> View.VISIBLE
            }
        }
    }

}