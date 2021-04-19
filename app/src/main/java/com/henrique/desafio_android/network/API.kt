package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    // Balance
    @GET("myBalance")
    suspend fun getBalance(): BalanceResponse

    // My Statement
    @GET("myStatement/{limit}/{offset}")
    suspend fun getMyStatement(
        @Path("limit") limit: String,
        @Path("offset") offset: String
    ): MyStatementResponseList

}