package br.com.andreviana.phi.desafioandroid.data.network.source

import br.com.andreviana.phi.desafioandroid.data.common.Constants.GENERIC_FAILED
import br.com.andreviana.phi.desafioandroid.data.common.DataState
import br.com.andreviana.phi.desafioandroid.data.model.*
import br.com.andreviana.phi.desafioandroid.data.network.service.StatementService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface StatementDataSource {
    suspend fun getBalance(): Flow<DataState<Balance>>
    suspend fun getStatement(
        limit: String,
        offset: String
    ): Flow<DataState<List<StatementViewList>>>

    suspend fun getStatementDetail(id: String): Flow<DataState<StatementViewDetail>>
}

class StatementDataSourceImpl @Inject constructor(
    private val statementService: StatementService
) : StatementDataSource {

    override suspend fun getBalance() = flow {
        emit(DataState.Loading)
        val response = statementService.getMyBalance()
        if (response.isSuccessful) {
            response.body()?.let { balance -> emit(DataState.Success(balance)) }
        } else {
            emit(DataState.Failure(response.code(), GENERIC_FAILED))
        }
    }.catch { emit(DataState.Error(it)) }

    override suspend fun getStatement(limit: String, offset: String) = flow {
        emit(DataState.Loading)
        val response = statementService.getMyStatement(limit = limit, offset = offset)
        if (response.isSuccessful) {
            response.body()
                ?.let { statement -> emit(DataState.Success(statement.mapperToItemsList())) }
        } else {
            emit(DataState.Failure(response.code(), GENERIC_FAILED))
        }
    }.catch { emit(DataState.Error(it)) }

    override suspend fun getStatementDetail(id: String) = flow {
        emit(DataState.Loading)
        val response = statementService.getMyStatementDetail(id = id)
        if (response.isSuccessful) {
            response.body()
                ?.let { statementDetail -> emit(DataState.Success(statementDetail.mapperToStatementDetail())) }
        } else {
            emit(DataState.Failure(response.code(), GENERIC_FAILED))
        }
    }.catch { emit(DataState.Error(it)) }
}