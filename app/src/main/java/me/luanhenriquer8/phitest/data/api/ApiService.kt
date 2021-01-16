package me.luanhenriquer8.phitest.data.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    /** Declaring all variables */
    private var retrofit: Retrofit = makeRetrofit()

    fun statement(): IStatementRepository =
        retrofit.create(IStatementRepository::class.java)

    fun balance(): IMyBalanceRepository = retrofit.create(IMyBalanceRepository::class.java)

    /** Method to create and an instance of retrofit */
    private fun makeRetrofit(vararg interceptors: Interceptor) = Retrofit.Builder()
        .baseUrl(Companion.BASE_URL)
        .client(makeHttpClient(interceptors))
        .addConverterFactory(GsonConverterFactory.create(createGsonConverter()))
        .build()

    /** This method creates the OkHttpClient with some configs */
    private fun makeHttpClient(interceptors: Array<out Interceptor>) = OkHttpClient.Builder()
        .addInterceptor(headersInterceptor())
        .apply { interceptors().addAll(interceptors) }
        .build()

    /** Method to create a Gson Builder */
    private fun createGsonConverter() = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .create()

    /** This method add headers on the request */
    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "token",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
                )
                .build()
        )
    }

    companion object {
        private const val BASE_URL = "https://desafio-mobile-bff.herokuapp.com/"
    }

}