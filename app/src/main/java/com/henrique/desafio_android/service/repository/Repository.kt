package com.henrique.desafio_android.service.repository

import com.henrique.desafio_android.service.model.balance.BalanceResponse
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponseList

interface Repository {

    suspend fun getBalance(): BalanceResponse
    suspend fun getMovimentation(limit: String, offset: String): MovimentationResponseList
    suspend fun getMovimentationDetail(id: String): MovimentationResponse

}