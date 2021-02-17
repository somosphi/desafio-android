package com.chavesdev.phiapp.views

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.util.formatNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*

class BalanceViewModel(private val accountRepo: AccountRepo) : ViewModel() {
    var isVisible = ObservableBoolean(true)
    var amount : MutableLiveData<String> = MutableLiveData("-")

    var showHideBalance = View.OnClickListener {
        isVisible.set(!isVisible.get())
    }

    fun loadBalance() {
        //check if is already loaded
        viewModelScope.launch(Dispatchers.IO){
            val response = accountRepo.getBalance()
            if(response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    amount.postValue(response.body()?.amount?.formatNumber())
                }
            }
        }
    }


}