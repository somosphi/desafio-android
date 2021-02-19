package com.chavesdev.phiapp.views

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
import com.chavesdev.phiapp.databinding.ActivityBalanceAndStatementsBinding
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BalanceAndStatementsActivity : AppCompatActivity(), ViewModelStoreOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding : ActivityBalanceAndStatementsBinding
    private lateinit var balanceViewModel : BalanceViewModel
    private lateinit var statementsViewModel: StatementsViewModel
    private lateinit var statementsPagingDataAdapter: StatementsPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_and_statements)
        binding.lifecycleOwner = this

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        balanceViewModel = ViewModelProvider(this, viewModelFactory).get(BalanceViewModel::class.java)
        statementsViewModel = ViewModelProvider(this, viewModelFactory).get(StatementsViewModel::class.java)
        statementsPagingDataAdapter = StatementsPageAdapter(StatementsPageAdapter.StatementComparator)
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
}