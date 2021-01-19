package com.ipsoft.ph.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ipsoft.ph.adapter.TransactionItemAdapter
import com.ipsoft.ph.databinding.ActivityStatementBinding
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.viewmodel.MainViewModel

class StatementActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var statementBiding: ActivityStatementBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statementBiding = ActivityStatementBinding.inflate(layoutInflater)
        val view = statementBiding.root
        setContentView(view)

        recyclerView = statementBiding.rvTransactions

        viewModel = ViewModelProvider(
            this, MainViewModel.StatementViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

        viewModel.transactionsLiveData.observe(this, Observer { transactions ->

            recyclerView.adapter = TransactionItemAdapter(transactions)




        })
        viewModel.getTransactions()



    }
}