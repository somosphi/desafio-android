package com.henrique.desafio_android.domain.repository

import com.henrique.desafio_android.domain.model.balance.BalanceResponse
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponseList

interface Repository {

    suspend fun getBalance(): BalanceResponse
    suspend fun getMovimentation(limit: String, offset: String): MovimentationResponseList
    suspend fun getMovimentationDetail(id: String): MovimentationResponse

}