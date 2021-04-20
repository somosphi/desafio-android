package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList

interface Repository {

    suspend fun getBalance(): BalanceResponse
    suspend fun getMyStatement(limit: String, offset: String): MyStatementResponseList
    suspend fun getStatementDetail(id: String): MyStatementResponse

}