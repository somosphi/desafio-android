package com.henrique.desafio_android.viewmodel

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.henrique.desafio_android.domain.model.RequestState

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