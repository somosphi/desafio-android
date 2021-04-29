package com.example.pedroneryphi.network

import com.example.pedroneryphi.BuildConfig
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val headers: Headers
        headers = request.headers().newBuilder()
            .add("token", BuildConfig.HEADER_TOKEN)
            .build()

        request = request.newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
}