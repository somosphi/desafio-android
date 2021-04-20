package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList

class RemoteDataSource(private val api: API) : Repository {

    override suspend fun getBalance(): BalanceResponse {
        val response = api.getBalance()
        requireNotNull(response.amount)
        return response
    }

    override suspend fun getMyStatement(limit: String, offset: String): MyStatementResponseList {
        val response = api.getMyStatement(limit, offset)
        requireNotNull(response.items)
        return response
    }

    override suspend fun getStatementDetail(id: String): MyStatementResponse {
        return api.getStatementDetail(id)
    }

}