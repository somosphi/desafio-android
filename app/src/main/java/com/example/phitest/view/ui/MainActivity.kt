package com.example.phitest.view.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phitest.R
import com.example.phitest.interfaces.OnItemClickListener
import com.example.phitest.interfaces.OnLoadMoreListener
import com.example.phitest.model.Amount
import com.example.phitest.model.Transaction
import com.example.phitest.model.retrofit.RetrofitInitializer
import com.example.phitest.util.VsFunctions
import com.example.phitest.view.adapter.TransactionRecyclerViewAdapter
import com.example.phitest.view.listeners.RecyclerViewInfiniteScroll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var scrollListener: RecyclerViewInfiniteScroll
    lateinit var adapter: TransactionRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    var offsetOld = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAmount()

        val callStatements = RetrofitInitializer.RetrofitService.instance.myStatements(VsFunctions.TOKEN, 10, 0)
        callStatements.enqueue(object: Callback<Transaction?> {
            override fun onResponse(call: Call<Transaction?>?,
                                    response: Response<Transaction?>?) {

                response?.body()?.let {
                    //val transactions: ArrayList<Transaction.Items> = it.items
                    //transactions = it.items
                    configureRecyclerView(it.items)
                }

            }

            override fun onFailure(call: Call<Transaction?>,
                                   t: Throwable?) {
                //TODO later
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })

    }

    private fun loadAmount(){

        val callAmount = RetrofitInitializer.RetrofitService.instance.myBalance(VsFunctions.TOKEN)
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

    }

    private fun configureRecyclerView(transactions: ArrayList<Transaction.Items?>){
        recyclerView = findViewById<View>(R.id.listTransactions) as RecyclerView
        adapter = TransactionRecyclerViewAdapter(transactions, this)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        scrollListener = RecyclerViewInfiniteScroll(recyclerView.layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun configureAmount(amount: Amount){
        val txtAmount = findViewById<TextView>(R.id.txtYourAmountValue)
        txtAmount.text = VsFunctions.formatToCurrency(amount.amount.toDouble())
    }

    private fun loadMoreData() {

        val offset = (((adapter.itemCount+10) / 10) - 1)
        if(offsetOld != offset) {
            offsetOld = offset

            adapter.addLoadingView()

            val callStatements = RetrofitInitializer.RetrofitService.instance.myStatements(VsFunctions.TOKEN, 10, offset)
            callStatements.enqueue(object : Callback<Transaction?> {
                override fun onResponse(call: Call<Transaction?>?,
                                        response: Response<Transaction?>?) {

                    response?.body()?.let {

                        adapter.removeLoadingView()

                        adapter.addData(it.items)

                        scrollListener.setLoaded()

                        adapter.notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<Transaction?>,
                                       t: Throwable?) {
                    //TODO later
                    t?.message?.let { Log.e("onFailure error", it) }
                }
            })
        }
    }

    override fun onItemClicked(item: Transaction.Items?) {
        Toast.makeText(this,"User name ${item?.id} \n Phone:${item?.description}",Toast.LENGTH_LONG)
                .show()
    }
}