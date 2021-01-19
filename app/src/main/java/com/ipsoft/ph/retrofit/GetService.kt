package com.ipsoft.ph.retrofit

import com.ipsoft.ph.repository.model.Balance
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.retrofit.Constants.BALANCE_URL
import com.ipsoft.ph.retrofit.Constants.EXTRACT_DETAIL_URL
import com.ipsoft.ph.retrofit.Constants.EXTRACT_URL
import com.ipsoft.ph.retrofit.Constants.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

interface GetService {
    @Headers("token: $TOKEN")
    @GET(BALANCE_URL)
     fun getBalance(): Call<Balance>

    @Headers("token: $TOKEN")
    @GET(EXTRACT_URL)
     fun getTransactions(): Call<List<Transaction>>

    @Headers("token: $TOKEN")
    @GET(EXTRACT_DETAIL_URL)
     fun getDetailTransaction(@Path("id") id: String): Call<Transaction>


}
