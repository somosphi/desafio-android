package com.example.desafiophi.features.bankStatementDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiophi.architecture.networking.Resource
import com.example.desafiophi.data.PhiService
import com.example.desafiophi.data.models.responses.StatementDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BankStatementDetailViewModel : ViewModel() {

    private val service = PhiService()
    private val _statementDetail = MutableLiveData<Resource<StatementDetail>>()

    val statementDetail: LiveData<Resource<StatementDetail>>
        get() = _statementDetail

    fun getStatementDetail(id: String) {
        _statementDetail.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getStatementDetail(id)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _statementDetail.value = Resource.success(response.body()!!)
                }
            }
        }
    }

}