package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import retrofit2.http.GET

interface API {

    // Balance
    @GET("myBalance")
    suspend fun getBalance(): BalanceResponse
}