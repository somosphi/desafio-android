package com.example.desafiophi.data

import com.example.desafiophi.data.models.responses.BalanceResponse
import com.example.desafiophi.data.models.responses.Statement
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
        @Path("limit") limit: Int = 10,
        @Path("offset") offset: Int = 0
    ): Response<Statement>

    companion object {
        private const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}