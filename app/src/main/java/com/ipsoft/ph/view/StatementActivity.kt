package com.ipsoft.ph.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ipsoft.ph.R
import com.ipsoft.ph.adapter.TransactionItemAdapter
import com.ipsoft.ph.databinding.ActivityStatementBinding
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.viewmodel.MainViewModel

class StatementActivity : AppCompatActivity() {

    private var showBalance: Boolean = true

    private lateinit var viewModel: MainViewModel
    private lateinit var statementBiding: ActivityStatementBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statementBiding = ActivityStatementBinding.inflate(layoutInflater)
        val view = statementBiding.root
        setContentView(view)

        recyclerView = statementBiding.rvTransactions
        val hideBalance = statementBiding.acountChart.imgHideBalance
        hideBalance.setOnClickListener {

            if(showBalance) {
                showBalance = false
                statementBiding.acountChart.txtPersonalBalanceField.visibility = View.INVISIBLE
                hideBalance.setImageResource(R.drawable.closedeye)
                statementBiding.acountChart.divider3.visibility = View.VISIBLE
            }else {
                showBalance = true
                statementBiding.acountChart.txtPersonalBalanceField.visibility = View.VISIBLE
                hideBalance.setImageResource(R.drawable.openeye)
                statementBiding.acountChart.divider3.visibility = View.INVISIBLE

            }


        }

        viewModel = ViewModelProvider(
            this, MainViewModel.StatementViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

        viewModel.getBalance()
        viewModel.getTransactions()
       // viewModel.getDetailTransaction("0B5BFD44-0DF1-4005-A7CF-66C9C0438380")

        viewModel.transactionsLiveData.observe(this, Observer { transactions ->

            recyclerView.adapter = TransactionItemAdapter(transactions)


        })
        viewModel.detailsLiveData.observe(this, Observer {

        })
        viewModel.balanceLiveData.observe(this, Observer { balance ->

            statementBiding.acountChart.txtPersonalBalanceField.text = "R$ ${balance.value}".replace(".",",")

        })

    }

}