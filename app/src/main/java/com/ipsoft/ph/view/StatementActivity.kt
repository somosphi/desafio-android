package com.ipsoft.ph.view

import android.content.Intent
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

import com.ipsoft.ph.util.CellClickListener
import com.ipsoft.ph.viewmodel.MainViewModel

class StatementActivity : AppCompatActivity(), CellClickListener {


    private var showBalance: Boolean = true

    private lateinit var viewModel: MainViewModel
    private lateinit var statementBiding: ActivityStatementBinding
    private lateinit var recyclerView: RecyclerView

    // Iniciando a RecyclerView
    var transactionItemAdapter: TransactionItemAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statementBiding = ActivityStatementBinding.inflate(layoutInflater)
        val view = statementBiding.root
        setContentView(view)

        initViewModel()
        initObservers()
        initOnClick()

    }

    private fun initRecyclerView(list: List<Transaction>) {

        recyclerView = statementBiding.rvTransactions
        transactionItemAdapter = TransactionItemAdapter(list, this)
        recyclerView.adapter = transactionItemAdapter
        recyclerView.setHasFixedSize(true)

        val llm = LinearLayoutManager(this)
        llm.isAutoMeasureEnabled = false
        recyclerView.layoutManager = llm


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
            this, MainViewModel.MainViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

    }

    private fun initObservers() {

        viewModel.getTransactions().observe(this, Observer {


            initRecyclerView(it.items)


        })

        viewModel.getBalance().observe(this, Observer { balance ->

            statementBiding.acountChart.txtPersonalBalanceField.text =
                "R$ ${balance.value}".replace(".", ",")

        })

    }

    override fun onCellClickListener(data: Transaction) {


                val intent = Intent(this, CheckingCopyAcitivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.putExtra("id",data.id)
                startActivity(intent)

            }
    }

