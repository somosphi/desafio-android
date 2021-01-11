package com.newton.phi.view.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.phi.R
import com.newton.phi.common.currencyDateCompleted
import com.newton.phi.common.currencyFormat
import com.newton.phi.common.isNetworkAvailable
import com.newton.phi.model.view.ItemDetail
import com.newton.phi.model.view.Transaction
import com.newton.phi.network.Interector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class TransactionViewModel(private val interector: Interector,private val context: Context) : ViewModel() {

    private val io = Dispatchers.IO
    private val main = Dispatchers.Main

    private val listTransaction = ArrayList<Transaction>()

    private val _balance = MutableLiveData("R$ 0,00")
    fun balanceCash() : LiveData<String> = _balance

    private val _look = MutableLiveData(true)
    fun lookCash() : LiveData<Boolean> = _look

    fun alterLookCash(){
        if (_look.value!!){
            _look.postValue(false)
        }else{
            _look.postValue(true)
        }
    }

    private val _internet = MutableLiveData<Boolean>()
    fun alertInternet() : LiveData<Boolean> = _internet

    fun getBalanceView() {
        _internet.postValue(false)
        _error.postValue(false)
        if (isNetworkAvailable(context)){
            viewModelScope.launch(io) {
                val result = interector.getBalance()
                if (result.isSuccessful){
                    Log.d("TransactionViewModel", result.body().toString())
                    result.body()?.let {
                        _balance.postValue("R$ ${currencyFormat(it.amount)}")
                    }
                }else{
                    _error.postValue(true)
                }

            }
        }else{
            _internet.postValue(true)
        }

    }

    private val _error = MutableLiveData<Boolean>()
    fun alertError() : LiveData<Boolean> = _error

    private val _listTransactions = MutableLiveData<List<Transaction>>()
    fun requestListTransaction() : LiveData<List<Transaction>> = _listTransactions

    private var request = 0

    private val _progress = MutableLiveData<Boolean>()
    fun progress() : LiveData<Boolean> = _progress

    fun getTransactions(){
        _internet.postValue(false)
        _progress.postValue(true)
        _error.postValue(false)
        if (isNetworkAvailable(context)){
            viewModelScope.launch(io){
                val process = interector.getTransactions("10", request.toString())
                if (process.isSuccessful){
                    var int = 1

                    process.body()!!.items.forEach {
                        Log.d("TransactionViewModel-", "$int = ${it}")
                        int++
                        listTransaction.add(it.toModel())
                    }
                    _listTransactions.postValue(listTransaction)
                    request++
                    _progress.postValue(false)
                }else{
                    _error.postValue(true)
                    _progress.postValue(false)
                }

            }
        }else{
            _internet.postValue(true)
            _progress.postValue(false)
        }
    }

    private var keyId = ""
    private val _listItem = MutableLiveData<List<ItemDetail>>()
    fun requestItemTransaction() : LiveData<List<ItemDetail>> = _listItem


    fun getItemTransaction(){
        _internet.postValue(false)
        _error.postValue(false)
        if (isNetworkAvailable(context)){
            viewModelScope.launch(io){
                val resultado = interector.getDetail(keyId)
                if (resultado.isSuccessful){
                    Log.d("resultadoitem", resultado.body().toString())
                    val item = resultado.body()!!
                    val list = ArrayList<ItemDetail>()
                    list.add(ItemDetail(title = context.getString(R.string.type_of_movement),description = item.description))
                    list.add(ItemDetail(title = context.getString(R.string.value),description = "R$ ${currencyFormat(item.amount)}"))
                    list.add(ItemDetail(title = context.getString(R.string.receiver),description = item.to))
                    list.add(ItemDetail(title = context.getString(R.string.date_hour),description = currencyDateCompleted(item.createdAt)))
                    list.add(ItemDetail(title = context.getString(R.string.authentication),description = item.authentication))
                    _listItem.postValue(list)
                }else{
                    _error.postValue(true)
                }

            }
        }else{
            _internet.postValue(true)
        }
    }

    fun setKeyId(string: String){
        keyId = string
    }


}