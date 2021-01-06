package com.example.desafiophi.data

import com.example.desafiophi.data.models.responses.BalanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PhiAPI {

    @GET("myBalance")
    suspend fun getMyBalance(@Header("token") token: String = TOKEN): Response<BalanceResponse>

    companion object {
        private const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}