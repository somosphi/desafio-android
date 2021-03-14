package br.com.andreviana.phi.desafioandroid.data.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.andreviana.phi.desafioandroid.data.common.Constants.GENERIC_FAILED
import br.com.andreviana.phi.desafioandroid.data.common.DataState
import br.com.andreviana.phi.desafioandroid.data.model.*
import br.com.andreviana.phi.desafioandroid.data.network.service.StatementService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_OFFSET = 0

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
) : StatementDataSource, PagingSource<Int, ItemResponse>() {

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
        emit(DataState.Success(response.mapperToItemsList()))
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

    override fun getRefreshKey(state: PagingState<Int, ItemResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemResponse> {
        val offset = params.key ?: STARTING_OFFSET
        return try {
            val response = statementService.getMyStatement(limit = "10", offset = offset.toString())
            val items = response.items
            LoadResult.Page(
                data = items,
                prevKey = if (offset == STARTING_OFFSET) null else offset - 1,
                nextKey = if (items.isEmpty()) null else offset + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}