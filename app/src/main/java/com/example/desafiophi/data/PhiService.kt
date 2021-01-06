package com.example.desafiophi.data

import com.example.desafiophi.data.models.responses.BalanceResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhiService {
    private val api: PhiAPI = Retrofit.Builder()
        .baseUrl("https://desafio-mobile-bff.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhiAPI::class.java)

    suspend fun getBalance(): Response<BalanceResponse> = api.getMyBalance()
}