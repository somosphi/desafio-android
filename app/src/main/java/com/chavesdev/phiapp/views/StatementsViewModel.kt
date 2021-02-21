package com.chavesdev.phiapp.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.repo.sources.StatementsPageSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatementsViewModel(private val accountRepo: AccountRepo) : ViewModel() {

    var statementDetail = MutableLiveData<StatementMessage>()

    val flow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        StatementsPageSource(accountRepo)
    }.flow.cachedIn(viewModelScope)

    fun getStatementDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO){
            val response = accountRepo.getStatementDetails(id)
            if(response.isSuccessful){
                withContext(Dispatchers.Main) {
                    statementDetail.value = response.body()
                }
            }
        }
    }
}