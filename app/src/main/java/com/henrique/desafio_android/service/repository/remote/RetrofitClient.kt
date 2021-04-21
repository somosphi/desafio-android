package com.henrique.desafio_android.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val baseUrl = "https://desafio-mobile-bff.herokuapp.com/"
    private const val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val newUrl = chain.request().url()
                .newBuilder()
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .header("token", token)
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return getRetrofitInstance().create(serviceClass)
    }

}