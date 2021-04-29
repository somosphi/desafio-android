package com.example.pedroneryphi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.pedroneryphi.base.BaseViewModel
import com.example.pedroneryphi.model.TransferDetail
import com.example.pedroneryphi.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository): BaseViewModel() {

    val transfers : MutableLiveData<List<TransferDetail>> = MutableLiveData()
    val balance : MutableLiveData<String> = MutableLiveData()

    fun findTransferList(limit: Int, offset: Int){
        repositoryDisposable = mainRepository.getTransferList(limit, offset).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                transfers.value = result.items
            }, {

            })
    }

    fun findBalance(){
        repositoryDisposable = mainRepository.getBalance().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if(result != null)
                    balance.value = result.amount.toString()
                else
                    balance.value = "0"
            }, {

            })
    }


}