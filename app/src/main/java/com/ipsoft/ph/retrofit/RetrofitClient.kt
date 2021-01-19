package com.ipsoft.ph.retrofit

import com.ipsoft.ph.repository.Constants
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

    const val MainServer = Constants.BASE_URL


    val retrofitClient: Retrofit.Builder by lazy {


        val okhttpClient = OkHttpClient.Builder()


        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val getService: GetService by lazy {
        retrofitClient
            .build()
            .create(GetService::class.java)
    }
}