package com.chavesdev.phiapp.repo.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chavesdev.phiapp.repo.AccountRepo
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import java.net.UnknownHostException

class StatementsPageSource(private val accountRepo: AccountRepo) :
    PagingSource<Int, StatementMessage>() {

    private val itemsPerPage = 10
    private val firstPage = 1

    override fun getRefreshKey(state: PagingState<Int, StatementMessage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StatementMessage> {
        return try {
            val page = params.key ?: firstPage
            val response = accountRepo.getStatements(itemsPerPage, page)
            var nextPage: Int? = page + 1
            if (response.body()?.items.isNullOrEmpty()) {
                nextPage = null
            }

            LoadResult.Page(response.body()?.items!!, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: UnknownHostException) {
            LoadResult.Error(e)
        }
    }
}