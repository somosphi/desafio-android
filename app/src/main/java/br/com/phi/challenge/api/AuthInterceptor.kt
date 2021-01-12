package br.com.phi.challenge.api

import br.com.phi.challenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by pcamilo on 10/01/2021.
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader("token", BuildConfig.APP_TOKEN).build()
        return chain.proceed(req)
    }
}