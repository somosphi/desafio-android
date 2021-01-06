package com.example.desafiophi.data

import com.example.desafiophi.data.models.responses.BalanceResponse
import com.example.desafiophi.data.models.responses.Statement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PhiService {
    var api: PhiAPI

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        api = Retrofit.Builder()
            .client(client)
            .baseUrl("https://desafio-mobile-bff.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhiAPI::class.java)
    }


    suspend fun getBalance(): Response<BalanceResponse> = api.getMyBalance()
    suspend fun getStatement(): Response<Statement> = api.getMyStatement()
}