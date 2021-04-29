package com.example.pedroneryphi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.pedroneryphi.base.BaseViewModel
import com.example.pedroneryphi.model.TransferDetailPresentation
import com.example.pedroneryphi.model.toPresentation
import com.example.pedroneryphi.repository.DetailRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    var transferDetail = MutableLiveData<TransferDetailPresentation>()

    fun findTransferDetail(id: String){
        repositoryDisposable = detailRepository.getTransferDetail(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                transferDetail.value = result.toPresentation()
            }, {

            })
    }

}