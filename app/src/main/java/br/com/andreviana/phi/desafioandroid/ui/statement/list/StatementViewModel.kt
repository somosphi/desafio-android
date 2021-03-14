package br.com.andreviana.phi.desafioandroid.ui.statement.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.andreviana.phi.desafioandroid.data.model.ItemResponse
import br.com.andreviana.phi.desafioandroid.data.repository.StatementRepository
import kotlinx.coroutines.flow.Flow

class StatementViewModel
@ViewModelInject constructor(
    private val statementRepository: StatementRepository
) : ViewModel() {

    fun getBalance() = liveData {
        emitSource(statementRepository.fetchBalance().asLiveData())
    }

    fun getStatement(limit: String, offset: String) = liveData {
        emitSource(statementRepository.fetchStatement(limit, offset).asLiveData())
    }

    fun getStatementPagination(): Flow<PagingData<ItemResponse>> {
        return statementRepository.getStatement().cachedIn(viewModelScope)
    }
}