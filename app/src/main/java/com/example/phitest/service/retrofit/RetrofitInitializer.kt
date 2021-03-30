package com.example.phitest.service.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    object RetrofitService {
        private const val BASE_URL = "https://desafio-mobile-bff.herokuapp.com/"

        private fun retrofitService(): Retrofit {
            return Retrofit.Builder()
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        val instance: TransactionInterface by lazy {
            retrofitService().create(TransactionInterface::class.java)
        }
    }
}