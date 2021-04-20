package com.henrique.desafio_android.network

import com.henrique.desafio_android.network.response.BalanceResponse
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.network.response.MyStatementResponseList
import retrofit2.http.GET
import retrofit2.http.Path

interface API {

    @GET("myBalance")
    suspend fun getBalance(): BalanceResponse

    @GET("myStatement/{limit}/{offset}")
    suspend fun getMyStatement(
        @Path("limit") limit: String,
        @Path("offset") offset: String
    ): MyStatementResponseList

    @GET("myStatement/detail/{id}")
    suspend fun getStatementDetail(
        @Path("id") id: String
    ): MyStatementResponse

}