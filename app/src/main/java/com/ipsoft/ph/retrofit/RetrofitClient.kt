package com.ipsoft.ph.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

object RetrofitClient {

    private const val MainServer = Constants.BASE_URL


    private val retrofitClient: Retrofit.Builder by lazy {


        val okHttpClient = OkHttpClient.Builder()


        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val getService: GetService by lazy {
        retrofitClient
            .build()
            .create(GetService::class.java)
    }
}