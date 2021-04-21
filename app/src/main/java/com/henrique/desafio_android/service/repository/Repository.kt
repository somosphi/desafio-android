package com.henrique.desafio_android.service.repository

import com.henrique.desafio_android.service.model.balance.BalanceResponse
import com.henrique.desafio_android.service.model.movimentation.MyStatementResponse
import com.henrique.desafio_android.service.model.movimentation.MyStatementResponseList

interface Repository {

    suspend fun getBalance(): BalanceResponse
    suspend fun getMyStatement(limit: String, offset: String): MyStatementResponseList
    suspend fun getStatementDetail(id: String): MyStatementResponse

}