package com.chavesdev.phiapp.views.statement_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.util.format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatementDetailViewModel(private val accountRepo: AccountRepo) : ViewModel() {

    var details = MutableLiveData<StatementMessage>()

    fun getDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = accountRepo.getStatementDetails(id)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    details.value = response.body()
                }
            }
        }
    }
}