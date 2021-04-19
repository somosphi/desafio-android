package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse

class RemoteDataSource(private val api: API) : Repository {

    override suspend fun getBalance(): BalanceResponse {
        val response = api.getBalance()
        requireNotNull(response.amount)
        return response
    }

}