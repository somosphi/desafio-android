package com.newton.phi.network

class Interector(private val retrofit: RetrofitInicializer) {

    suspend fun getBalance() = retrofit.requestApi().getBalance()

    suspend fun getTransactions(limit : String, offset : String) = retrofit.requestApi().getMyStatement(limit = limit,offset = offset)

    suspend fun getDetail(key : String) = retrofit.requestApi().getMyStatementDetail(id = key)

}