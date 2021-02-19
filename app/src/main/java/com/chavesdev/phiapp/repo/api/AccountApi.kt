package com.chavesdev.phiapp.repo.api

import com.chavesdev.phiapp.repo.api.messages.BalanceMessage
import com.chavesdev.phiapp.repo.api.messages.ListStatementsResponse
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountApi {

    @GET(Constants.Endpoints.myBalance)
    suspend fun getBalance(): Response<BalanceMessage>

    @GET(Constants.Endpoints.myStatement)
    suspend fun getStatements(
        @Path("limit") limit: Int,
        @Path("offset") offset: Int
    ): Response<ListStatementsResponse>
}