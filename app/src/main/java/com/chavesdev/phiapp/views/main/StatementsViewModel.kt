package com.chavesdev.phiapp.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.repo.sources.StatementsPageSource

class StatementsViewModel(private val accountRepo: AccountRepo) : ViewModel() {

    val flow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        StatementsPageSource(accountRepo)
    }.flow.cachedIn(viewModelScope)
}