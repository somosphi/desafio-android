package com.example.pedroneryphi.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel protected constructor() : ViewModel() {
    var repositoryDisposable: Disposable? = null

    public override fun onCleared() {
        super.onCleared()
        repositoryDisposable?.dispose()
    }
}