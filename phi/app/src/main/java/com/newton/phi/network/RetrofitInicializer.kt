package com.newton.phi.network

import com.newton.phi.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInicializer {
    private var client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .header("token",Constants.baseToken)
                        .method(original.method(),original.body())
                        .build()
                chain.proceed(request)
            }
            .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun requestApi() = retrofit.create(ApiServices::class.java)

}