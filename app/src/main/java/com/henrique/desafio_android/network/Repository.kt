package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList

interface Repository {

    suspend fun getBalance(): BalanceResponse
    suspend fun getMyStatement(limit: String, offset: String): MyStatementResponseList

}