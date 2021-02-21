package com.chavesdev.phiapp.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.R
import com.chavesdev.phiapp.databinding.ActivityMainBinding
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.views.StatementsPageAdapter
import com.chavesdev.phiapp.views.StatementsViewModel
import com.chavesdev.phiapp.views.statement_details.StatementDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewModelStoreOwner,
    StatementsPageAdapter.StatementListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding : ActivityMainBinding
    private lateinit var balanceViewModel : BalanceViewModel
    private lateinit var statementsViewModel: StatementsViewModel
    private lateinit var statementsPagingDataAdapter: StatementsPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        balanceViewModel = ViewModelProvider(this, viewModelFactory).get(BalanceViewModel::class.java)
        statementsViewModel = ViewModelProvider(this, viewModelFactory).get(StatementsViewModel::class.java)
        statementsPagingDataAdapter = StatementsPageAdapter(StatementsPageAdapter.StatementComparator, this)
        binding.recyclerStatements.adapter = statementsPagingDataAdapter
        binding.recyclerStatements.layoutManager = LinearLayoutManager(this)

        binding.balance = balanceViewModel
        binding.loading = true

        balanceViewModel.loadBalance()

        loadStatements()
    }

    private fun loadStatements() {
        lifecycleScope.launch {
            statementsViewModel.flow.collectLatest { pagingData ->
                statementsPagingDataAdapter.submitData(pagingData)
            }

            statementsPagingDataAdapter.loadStateFlow.collectLatest { loadStates ->
                when(loadStates.refresh) {
                    LoadState.Loading -> binding.loading = true
                    is Error -> binding.loading = false
                    else -> binding.loading = false
                }
            }
        }
    }

    override fun onStatementClick(id: String) {
        Intent(this, StatementDetailsActivity::class.java).apply {
            putExtra(StatementDetailsActivity.statementId, id)
            startActivity(this)
        }
    }
}