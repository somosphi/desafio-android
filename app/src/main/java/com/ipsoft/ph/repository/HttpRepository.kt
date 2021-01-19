package com.ipsoft.ph.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ipsoft.ph.repository.model.Balance
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

object HttpRepository {

    val transactionsLiveData = MutableLiveData<List<Transaction>>()
    val balanceLiveData = MutableLiveData<Balance>()
    val detailsLiveData = MutableLiveData<Transaction>()


    fun getBalance(): MutableLiveData<Balance> {

        val call = RetrofitClient.getService.getBalance()

        call.enqueue(object : Callback<Balance> {

            override fun onFailure(call: Call<Balance>, t: Throwable) {
                Log.e("Anthoni", "getBalance Fail")
            }

            override fun onResponse(call: Call<Balance>, response: Response<Balance>) {

                val data = response.body()

                val balance = data?.value

                balanceLiveData.value = Balance(balance ?: 0.0)

            }


        })

        return balanceLiveData


    }

    fun getTransactions(): MutableLiveData<List<Transaction>> {

        val call = RetrofitClient.getService.getTransactions()

        call.enqueue(object : Callback<List<Transaction>> {
            override fun onResponse(
                call: Call<List<Transaction>>,
                response: Response<List<Transaction>>
            ) {
                val data = response.body()

                transactionsLiveData.value = data
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                Log.e("Anthoni", "getTransactions Fail")
            }

        })

        return transactionsLiveData


    }


    fun getDetailTransaction(id: String): MutableLiveData<Transaction> {

        val call = RetrofitClient.getService.getDetailTransaction(id)

        call.enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                val data = response.body()
                detailsLiveData.value = data
            }

            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                Log.e("Anthoni", "getDetails Fail")
            }

        })


        return detailsLiveData


    }
}


