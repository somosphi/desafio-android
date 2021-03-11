package br.com.andreviana.phi.desafioandroid.data.network.service

import br.com.andreviana.phi.desafioandroid.data.common.Constants.TOKEN
import br.com.andreviana.phi.desafioandroid.data.model.Balance
import br.com.andreviana.phi.desafioandroid.data.model.ItemResponse
import br.com.andreviana.phi.desafioandroid.data.model.StatementResponse
import br.com.andreviana.phi.desafioandroid.data.network.Endpoints.BALANCE
import br.com.andreviana.phi.desafioandroid.data.network.Endpoints.STATEMENT
import br.com.andreviana.phi.desafioandroid.data.network.Endpoints.STATEMENT_DETAIL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StatementService {

    @GET(BALANCE)
    suspend fun getMyBalance(@Header("token") token: String = TOKEN): Response<Balance>

    @GET(STATEMENT)
    suspend fun getMyStatement(
        @Header("token") token: String = TOKEN,
        @Path("limit") limit: String,
        @Path("offset") offset: String
    ): Response<StatementResponse>

    @GET(STATEMENT_DETAIL)
    suspend fun getMyStatementDetail(
        @Header("token") token: String = TOKEN,
        @Path("id") id: String
    ): Response<ItemResponse>
}