package com.newton.phi.network

import com.newton.phi.model.network.ResponseBalance
import com.newton.phi.model.network.ResponseDetailTransaction
import com.newton.phi.model.network.ResponseTransactions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("/myBalance")
    suspend fun getBalance(): Response<ResponseBalance>

    @GET("/myStatement/{limit}/{offset}")
    suspend fun getMyStatement(
            @Path("limit") limit: String,
            @Path("offset") offset: String
    ): Response<ResponseTransactions>

    @GET("myStatement/detail/{id}/")
    suspend fun getMyStatementDetail(@Path("id") id: String): Response<ResponseDetailTransaction>

}