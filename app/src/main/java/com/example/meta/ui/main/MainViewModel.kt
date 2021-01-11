package com.example.meta.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meta.BaseViewModel
import com.example.meta.data.repository.main.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository): BaseViewModel() {
    private val _states = MutableLiveData<MainViewState>()
    val states: LiveData<MainViewState>
        get() = _states

    fun loadAmount(token: String) {
        launch {
            _states.value = MainViewState.ShowLoading(true)
            repository.doRequestGetAmount(
                token,
                { amountResponse ->
                    _states.value = MainViewState.ShowAmount(amountResponse)
                    _states.value = MainViewState.ShowLoading(false)
                },
                {
                    _states.value = MainViewState.ShowError(true)
                    _states.value = MainViewState.ShowLoading(false)
                })
        }
    }

    fun loadStatement(token: String, limit: Int, offset: Int) {
        launch {
            _states.value = MainViewState.ShowLoading(true)
            repository.doRequestGetStatement(
                    token,
                    limit,
                    offset,
                    { itemsResponse ->
                        _states.value = MainViewState.ShowStatement(itemsResponse)
                        _states.value = MainViewState.ShowLoading(false)
                    },
                    {
                        _states.value = MainViewState.ShowError(true)
                        _states.value = MainViewState.ShowLoading(false)
                    })
        }
    }

    fun loadStatementDetails(token: String, id: String) {
        launch {
            _states.value = MainViewState.ShowLoading(true)
            repository.doRequestGetDetailsStatement(
                    token,
                    id,
                    { detailsResponse ->
                        _states.value = MainViewState.ShowStatementDetails(detailsResponse)
                        _states.value = MainViewState.ShowLoading(false)
                    },
                    {
                        _states.value = MainViewState.ShowError(true)
                        _states.value = MainViewState.ShowLoading(false)
                    })
        }
    }
}