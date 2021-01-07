package me.luanhenriquer8.phitest.data.api

import me.luanhenriquer8.phitest.data.models.Balance
import retrofit2.Call
import retrofit2.http.GET

interface IMyBalanceRepository {

    @GET("myBalance")
    fun get(): Call<Balance>
}