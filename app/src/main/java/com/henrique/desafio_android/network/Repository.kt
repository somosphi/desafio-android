package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse

interface Repository {

    suspend fun getBalance(): BalanceResponse

}