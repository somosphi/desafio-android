package com.example.phitest.service.retrofit

import com.example.phitest.service.model.Amount
import com.example.phitest.service.model.Transaction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface TransactionInterface {

    @GET("myBalance")
    fun myBalance(@Header("token") token: String) : Call<Amount>

    @GET("myStatement/10/0")
    fun myStatements(@Header("token") token: String) : Call<Transaction>
}