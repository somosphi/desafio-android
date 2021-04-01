package com.example.phitest.interfaces

import com.example.phitest.model.Amount
import com.example.phitest.model.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TransactionInterface {

    @GET("myBalance")
    fun myBalance(@Header("token") token: String) : Call<Amount>

    @GET("myStatement/{limit}/{offset}")
    fun myStatements(@Header("token") token: String, @Path("limit") limit: Int, @Path("offset") offset: Int) : Call<Transaction>
}