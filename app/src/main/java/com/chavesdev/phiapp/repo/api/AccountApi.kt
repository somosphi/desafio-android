package com.chavesdev.phiapp.repo.api

import com.chavesdev.phiapp.repo.api.messages.BalanceMessage
import com.chavesdev.phiapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface AccountApi {

    @GET(Constants.Endpoints.myBalance)
    suspend fun getBalance() : Response<BalanceMessage>
}