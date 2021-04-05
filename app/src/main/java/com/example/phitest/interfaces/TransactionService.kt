package com.example.phitest.interfaces

import com.example.phitest.model.Transaction
import com.example.phitest.model.TransactionDetail
import com.example.phitest.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TransactionService {

    @GET("myBalance")
    suspend fun myBalance(@Header("token") token: String) : Response<User>

    @GET("myStatement/{limit}/{offset}")
    suspend fun myStatements(@Header("token") token: String, @Path("limit") limit: Int, @Path("offset") offset: Int) : Response<Transaction>

    @GET("myStatement/detail/{id}")
    suspend fun myStatementDetail(@Header("token") token: String, @Path("id") id: String) : Response<TransactionDetail>
}