package br.com.phi.challenge.api.statement.data.remote

import br.com.phi.challenge.api.statement.data.model.StatementItemResponse
import br.com.phi.challenge.api.statement.data.model.StatementResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by pcamilo on 10/01/2021.
 */
interface StatementService {

    /**
     * Get all statements
     */
    @GET("myStatement/{limit}/{offset}")
    suspend fun getStatements(@Path("limit") limit: Int, @Path("offset") offset: Int): Response<StatementResponse>

    /**
     * Get statement detail
     */
    @GET("myStatement/detail/{id}")
    suspend fun getStatementDetail(@Path("id") id: String): Response<StatementItemResponse>
}