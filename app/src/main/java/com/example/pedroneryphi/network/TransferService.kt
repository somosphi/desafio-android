package com.example.pedroneryphi.network

import com.example.pedroneryphi.model.Balance
import com.example.pedroneryphi.model.TransferDetail
import com.example.pedroneryphi.model.TransferList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransferService {

    @GET("myStatement/{limit}/{offset}")
    fun getTransferList(@Path("limit") limit: Int,
                        @Path("offset") offset : Int): Observable<TransferList>

    @GET("myStatement/detail/{id}")
    fun getTransfer(@Path("id") id : String): Observable<TransferDetail>

    @GET("myBalance")
    fun getBalance(): Observable<Balance>

}