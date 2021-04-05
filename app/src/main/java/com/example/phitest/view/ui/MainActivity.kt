package com.example.phitest.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phitest.AppDatabase
import com.example.phitest.R
import com.example.phitest.interfaces.OnItemClickListener
import com.example.phitest.interfaces.OnLoadMoreListener
import com.example.phitest.model.AmountEntity
import com.example.phitest.model.Transaction
import com.example.phitest.model.User
import com.example.phitest.util.VsFunctions
import com.example.phitest.view.adapter.TransactionRecyclerViewAdapter
import com.example.phitest.view.listeners.RecyclerViewInfiniteScroll
import com.example.phitest.view.viewmodel.Result
import com.example.phitest.view.viewmodel.TransactionDetailViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var scrollListener: RecyclerViewInfiniteScroll
    lateinit var adapter: TransactionRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    var offsetOld = 0

    private val viewModelTransaction: TransactionDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val actionBar = supportActionBar
        actionBar?.title = ""

        val pbLoadingData = findViewById<ProgressBar>(R.id.pbLoadingData)
        pbLoadingData.visibility = View.VISIBLE

        loadAmount()
        defineAmountVisibility()

        viewModelTransaction.myStatements(VsFunctions.TOKEN, 10,0).observe(this){
            it?.let { result ->
                when(result){
                    is Result.Success -> {
                        result.data?.let { transactions ->
                            configureRecyclerView(transactions.items)
                        }

                        runOnUiThread(java.lang.Runnable {
                            pbLoadingData.visibility = View.GONE
                        })
                    }
                    is Result.Error -> {
                        Toast.makeText(this, VsFunctions.isOnline(this), Toast.LENGTH_SHORT).show()

                        runOnUiThread(java.lang.Runnable {
                            pbLoadingData.visibility = View.GONE
                        })
                    }
                }
            }
        }

        /*val callStatements = RetrofitInitializer.RetrofitService.instance.myStatements(VsFunctions.TOKEN, 10, 0)
        callStatements.enqueue(object: Callback<Transaction?> {
            override fun onResponse(call: Call<Transaction?>?,
                                    response: Response<Transaction?>?) {

                response?.body()?.let {
                    configureRecyclerView(it.items)
                }

                runOnUiThread(java.lang.Runnable {
                    pbLoadingData.visibility = View.GONE
                })
            }

            override fun onFailure(call: Call<Transaction?>,
                                   t: Throwable?) {
                //TODO later
                t?.message?.let { Log.e("onFailure error", it) }

                runOnUiThread(java.lang.Runnable {
                    pbLoadingData.visibility = View.GONE
                })
            }

        })*/

        val imgbtnSaldo = findViewById<ImageButton>(R.id.imgbtnSaldo)
        imgbtnSaldo.setOnClickListener {
            switchAmountVisibility()
        }
    }

    private fun defineAmountVisibility() {

        val db = AppDatabase(this)

        GlobalScope.launch {

            val data = db.amountDao().getAmount()
            val txtYourAmountValue = findViewById<TextView>(R.id.txtYourAmountValue)
            val progressBarAmount = findViewById<ProgressBar>(R.id.progressBarAmount)

            if(data.isEmpty()){
                db.amountDao().insertAmount(AmountEntity(0, 0, true))

                runOnUiThread(java.lang.Runnable {
                    txtYourAmountValue.visibility = View.VISIBLE
                    progressBarAmount.visibility = View.GONE
                })

            }else{

                runOnUiThread(java.lang.Runnable {
                    if(data[0].showAmount) {
                        txtYourAmountValue.visibility = View.VISIBLE
                        progressBarAmount.visibility = View.GONE
                    }else{
                        txtYourAmountValue.visibility = View.GONE
                        progressBarAmount.visibility = View.VISIBLE
                    }
                })

            }
        }
    }

    private fun switchAmountVisibility(){

        val db = AppDatabase(this)

        GlobalScope.launch {

            val data = db.amountDao().getAmount()
            data.forEach {
                it.showAmount = !it.showAmount
            }
            db.amountDao().updateAmount(data[0])

            val txtYourAmountValue = findViewById<TextView>(R.id.txtYourAmountValue)
            val progressBarAmount = findViewById<ProgressBar>(R.id.progressBarAmount)
            val imgbtnSaldo = findViewById<ImageButton>(R.id.imgbtnSaldo)

            runOnUiThread(java.lang.Runnable {
                if (data[0].showAmount) {
                    txtYourAmountValue.visibility = View.VISIBLE
                    progressBarAmount.visibility = View.GONE
                    imgbtnSaldo.setImageResource(R.drawable.baseline_visibility_black_24)
                } else {
                    txtYourAmountValue.visibility = View.GONE
                    progressBarAmount.visibility = View.VISIBLE
                    imgbtnSaldo.setImageResource(R.drawable.baseline_visibility_off_black_24)
                }
            })

        }
    }

    private fun loadAmount(){

        viewModelTransaction.myBalance(VsFunctions.TOKEN).observe(this){
            it?.let { result ->
                when(result){
                    is Result.Success -> {
                        result.data?.let { amount ->
                            configureAmount(amount)
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this, VsFunctions.isOnline(this), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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

    private fun configureAmount(user: User){
        val txtAmount = findViewById<TextView>(R.id.txtYourAmountValue)
        txtAmount.text = VsFunctions.formatToCurrency(user.amount.toDouble())
    }

    private fun loadMoreData() {

        val offset = (((adapter.itemCount+10) / 10) - 1)
        if(offsetOld != offset) {
            offsetOld = offset

            adapter.addLoadingView()

            viewModelTransaction.myStatements(VsFunctions.TOKEN, 10,offset).observe(this){
                it?.let { result ->
                    when(result){
                        is Result.Success -> {
                            result.data?.let { transactions ->
                                //configureRecyclerView(transactions.items)
                                adapter.removeLoadingView()

                                adapter.addData(transactions.items)

                                scrollListener.setLoaded()

                                adapter.notifyDataSetChanged()
                            }
                        }
                        is Result.Error -> {
                            Toast.makeText(this, VsFunctions.isOnline(this), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            /*val callStatements = RetrofitInitializer.RetrofitService.instance.myStatements(VsFunctions.TOKEN, 10, offset)
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
            })*/
        }
    }

    override fun onItemClicked(item: Transaction.Items) {
        val intent = Intent(this, Detail::class.java)
        intent.putExtra("transaction", item)
        startActivity(intent)
    }
}