package com.example.meta.source.remote

import com.example.meta.data.model.AmountResponse
import com.example.meta.data.model.DetailsResponse
import com.example.meta.data.model.ItemsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Api {

    companion object ServiceBuilder {
        /** Retorna uma Instância do Client Retrofit para Requisições
         * @param path Caminho Principal da API
         */
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        private const val BASE_URL = "https://desafio-mobile-bff.herokuapp.com/"
    }

    @GET("myBalance")
    fun getAmount(
        @Header("token") token: String
    ): Call<AmountResponse>

    @GET("myStatement/{limit}/{offset}")
    fun getMyStatement(
        @Header("token") token: String,
        @Path("limit") limit: Int,
        @Path("offset") offset: Int
    ): Call<ItemsResponse>

    @GET("myStatement/detail/{id}")
    fun getStatementDetail(
            @Header("token") token: String,
            @Path("id") id: String
    ): Call<DetailsResponse>
}