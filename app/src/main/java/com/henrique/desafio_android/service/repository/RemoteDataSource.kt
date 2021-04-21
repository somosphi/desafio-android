package com.henrique.desafio_android.service.repository

import com.henrique.desafio_android.service.model.balance.BalanceResponse
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponseList
import com.henrique.desafio_android.service.repository.remote.API

class RemoteDataSource(private val api: API) : Repository {

    override suspend fun getBalance(): BalanceResponse {
        val response = api.getBalance()
        requireNotNull(response.amount)
        return response
    }

    override suspend fun getMovimentation(limit: String, offset: String): MovimentationResponseList {
        val response = api.getMovimentation(limit, offset)
        requireNotNull(response.items)
        return response
    }

    override suspend fun getMovimentationDetail(id: String): MovimentationResponse {
        return api.getMovimentationDetail(id)
    }

}