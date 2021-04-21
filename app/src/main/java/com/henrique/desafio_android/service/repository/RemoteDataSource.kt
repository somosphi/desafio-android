package com.henrique.desafio_android.service.repository

import com.henrique.desafio_android.service.model.balance.BalanceResponse
import com.henrique.desafio_android.service.model.movimentation.MyStatementResponse
import com.henrique.desafio_android.service.model.movimentation.MyStatementResponseList
import com.henrique.desafio_android.service.repository.Repository
import com.henrique.desafio_android.service.repository.remote.API

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