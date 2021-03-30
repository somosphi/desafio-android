package com.example.phitest.view.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phitest.R
import com.example.phitest.service.model.Amount
import com.example.phitest.service.model.Transaction
import com.example.phitest.service.retrofit.RetrofitInitializer
import com.example.phitest.view.adapter.TransactionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var transactions: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        /*transactions = Transaction.createTransactonsList();
        val adapter = TransactionAdapter(transactions)

        val rvTransactions = findViewById<View>(R.id.listTransactions) as RecyclerView
        rvTransactions.adapter = adapter
        rvTransactions.layoutManager = LinearLayoutManager(this)*/


    }

    private fun loadData(){
        val callAmount = RetrofitInitializer.RetrofitService.instance.myBalance(getString(R.string.token))
        callAmount.enqueue(object: Callback<Amount?> {
            override fun onResponse(call: Call<Amount?>?,
                                    response: Response<Amount?>?) {

                response?.body()?.let {
                    val amount: Amount = it
                    configureAmount(amount)
                }

            }

            override fun onFailure(call: Call<Amount?>,
                                   t: Throwable?) {
                //TODO later
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })

        val callStatements = RetrofitInitializer.RetrofitService.instance.myStatements(getString(R.string.token))
        callStatements.enqueue(object: Callback<Transaction?> {
            override fun onResponse(call: Call<Transaction?>?,
                                    response: Response<Transaction?>?) {

                response?.body()?.let {
                    val transactions: List<Transaction.Items> = it.items
                    configureTransactions(transactions)
                }

            }

            override fun onFailure(call: Call<Transaction?>,
                                   t: Throwable?) {
                //TODO later
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun configureTransactions(transactions: List<Transaction.Items>){
        val recyclerView = findViewById<View>(R.id.listTransactions) as RecyclerView
        recyclerView.adapter = TransactionAdapter(transactions, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //recyclerView.layoutManager = layoutManager

    }

    private fun configureAmount(amount: Amount){
        val txtAmount = findViewById<TextView>(R.id.txtYourAmountValue)
        txtAmount.text = amount.amount.toString()

    }
}