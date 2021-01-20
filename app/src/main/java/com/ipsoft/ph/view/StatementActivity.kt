package com.ipsoft.ph.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ipsoft.ph.R
import com.ipsoft.ph.adapter.TransactionItemAdapter
import com.ipsoft.ph.databinding.ActivityStatementBinding
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.viewmodel.MainViewModel

class StatementActivity : AppCompatActivity() {

    private var showBalance: Boolean = true

    private lateinit var viewModel: MainViewModel
    private lateinit var statementBiding: ActivityStatementBinding
    private lateinit var recyclerView: RecyclerView

    // Iniciando a RecyclerView
    var transactionItemAdapter: TransactionItemAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statementBiding = ActivityStatementBinding.inflate(layoutInflater)
        val view = statementBiding.root
        setContentView(view)




        initViewModel()
        initObservers()
        initOnClick()





    }

    private fun initRecyclerView( list: List<Transaction>) {

        transactionItemAdapter = TransactionItemAdapter(list)
        recyclerView = statementBiding.rvTransactions
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = transactionItemAdapter

    }



    private fun initOnClick() {
        val hideBalance = statementBiding.acountChart.imgHideBalance
        hideBalance.setOnClickListener {

            if (showBalance) {
                showBalance = false
                statementBiding.acountChart.txtPersonalBalanceField.visibility = View.INVISIBLE
                hideBalance.setImageResource(R.drawable.closedeye)
                statementBiding.acountChart.divider3.visibility = View.VISIBLE
            } else {
                showBalance = true
                statementBiding.acountChart.txtPersonalBalanceField.visibility = View.VISIBLE
                hideBalance.setImageResource(R.drawable.openeye)
                statementBiding.acountChart.divider3.visibility = View.INVISIBLE

            }


        }
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this, MainViewModel.StatementViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

    }

    private fun initObservers() {

        viewModel.getTransactions().observe(this, Observer {

            initRecyclerView(HttpRepository.transactionsList)



        })

        viewModel.getDetailTransaction("0B5BFD44-0DF1-4005-A7CF-66C9C0438380")
            .observe(this, Observer {

            })
        viewModel.getBalance().observe(this, Observer { balance ->

            statementBiding.acountChart.txtPersonalBalanceField.text =
                "R$ ${balance.value}".replace(".", ",")

        })

    }

}