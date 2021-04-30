package com.example.pedroneryphi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedroneryphi.base.BaseViewModel
import com.example.pedroneryphi.model.TransferDetailPresentation
import com.example.pedroneryphi.model.toPresentation
import com.example.pedroneryphi.repository.DetailRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private var _transferDetail = MutableLiveData<TransferDetailPresentation>()
    val transferDetail: LiveData<TransferDetailPresentation>
            get() = _transferDetail

    fun findTransferDetail(id: String){
        repositoryDisposable = detailRepository.getTransferDetail(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _transferDetail.value = result.toPresentation()
            }, {

            })
    }

}