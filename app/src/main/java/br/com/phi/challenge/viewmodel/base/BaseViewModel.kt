package br.com.phi.challenge.viewmodel.base

import android.app.Application
import android.view.View
import android.view.View.GONE
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import br.com.phi.challenge.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by pcamilo on 10/01/2021.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app), DefaultLifecycleObserver {

    val loading = ObservableField(GONE)
    val errorVisibility = ObservableField(GONE)
    internal val errorMessage = MutableLiveData<Int>(R.string.string_empty)
    val msgError : LiveData<Int> = errorMessage

    protected fun executeAsync(showLoading: Boolean = true, service: suspend (() -> Unit)) : Job {

        if (showLoading) {
            loading.set(View.VISIBLE)
        }

        return viewModelScope.launch {
            try {
                service.invoke()
                loading.set(GONE)
            } catch (e: Exception) {
                loading.set(GONE)
            }
        }
    }

}