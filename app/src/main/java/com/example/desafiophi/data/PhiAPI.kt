package com.example.desafiophi.data

import com.example.desafiophi.data.models.responses.BalanceResponse
import com.example.desafiophi.data.models.responses.Statement
import com.example.desafiophi.data.models.responses.StatementDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PhiAPI {

    @GET("myBalance")
    suspend fun getMyBalance(@Header("token") token: String = TOKEN): Response<BalanceResponse>

    @GET("myStatement/{limit}/{offset}")
    suspend fun getMyStatement(
        @Header("token") token: String = TOKEN,
        @Path("limit") limit: Int = PAGE_SIZE,
        @Path("offset") offset: Int = PAGE_NUMBER
    ): Response<Statement>

    @GET("myStatement/detail/{id}")
    suspend fun getStatementDetail(
        @Header("token") token: String = TOKEN,
        @Path("id") id: String
    ): Response<StatementDetail>

    companion object {
        private const val PAGE_SIZE = 10
        private const val PAGE_NUMBER = 0
        private const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}