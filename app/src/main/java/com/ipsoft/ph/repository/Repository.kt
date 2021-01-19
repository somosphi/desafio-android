package com.ipsoft.ph.repository

import com.ipsoft.ph.repository.model.Transaction
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

interface Repository {
    @Headers("token: $TOKEN")
    @GET(Constants.BASE_URL + Constants.BALANCE_URL)
    fun getBalance(): Double

    @Headers("token: $TOKEN")
    @GET(Constants.BASE_URL + Constants.EXTRACT_URL)
    fun getStatement(): List<Transaction>

    @Headers("token: $TOKEN")
    @GET(Constants.BASE_URL + Constants.EXTRACT_DETAIL_URL)
    fun getDetailStatement(id: String): Transaction

    companion object {
        const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}
