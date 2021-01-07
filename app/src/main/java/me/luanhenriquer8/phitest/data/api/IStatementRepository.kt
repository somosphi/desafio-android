package me.luanhenriquer8.phitest.data.api

import me.luanhenriquer8.phitest.data.models.Statement
import me.luanhenriquer8.phitest.data.models.Transaction
import me.luanhenriquer8.phitest.utils.SIZE_REQUEST_LIST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IStatementRepository {

    @GET("myStatement/${SIZE_REQUEST_LIST}/{offset}")
    fun get(@Path("offset") offset: Int): Call<Transaction>

    @GET("myStatement/detail/{statementId}")
    fun get(@Path("statementId") statementId: String): Call<Statement>

}