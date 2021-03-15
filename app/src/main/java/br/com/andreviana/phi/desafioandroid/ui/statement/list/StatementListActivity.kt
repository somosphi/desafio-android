package br.com.andreviana.phi.desafioandroid.ui.statement.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.common.Constants
import br.com.andreviana.phi.desafioandroid.data.common.DataState
import br.com.andreviana.phi.desafioandroid.data.model.mapperToStatementList
import br.com.andreviana.phi.desafioandroid.databinding.ActivityStatementListBinding
import br.com.andreviana.phi.desafioandroid.ui.statement.list.adapter.StatementAdapter
import br.com.andreviana.phi.desafioandroid.ui.statement.list.adapter.StatementLoadAdapter
import br.com.andreviana.phi.desafioandroid.util.helper.PreferencesHelper
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat
import br.com.andreviana.phi.desafioandroid.util.ktx.navigationToStatementDetail
import br.com.andreviana.phi.desafioandroid.util.ktx.showToastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatementListActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: StatementViewModel by viewModels()
    private val binding by lazy { ActivityStatementListBinding.inflate(layoutInflater) }
    private val preferences: PreferencesHelper by lazy { PreferencesHelper(applicationContext) }

    private val adapter: StatementAdapter by lazy { StatementAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getMyBalance()
        setupUI()
        getMyStatement()
    }

    private fun setupUI() {
        binding.imageViewHideBalance.setOnClickListener(this)
        binding.imageViewShowBalance.setOnClickListener(this)
        binding.recyclerViewMoves.adapter = adapter.withLoadStateHeaderAndFooter(
            header = StatementLoadAdapter { adapter.retry() },
            footer = StatementLoadAdapter { adapter.retry() }
        )
        checkVisibilityBalance()
        checkUiNightMode()

        adapter.runOnItemClickListener { statementId ->
            navigationToStatementDetail(statementId)
        }
    }

    private fun checkVisibilityBalance() {
        if (preferences.getVisibilityBalance()) showBalance()
        else hideBalance()
    }

    private fun checkUiNightMode() {
        val mode =
            binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            binding.cardViewBalance.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.grey_custom_900
                )
            )
        }
    }

    private fun showBalance() {
        preferences.setVisibilityBalance(true)
        binding.imageViewHideBalance.visibility = View.VISIBLE
        binding.textViewBalanceValue.visibility = View.VISIBLE
        binding.imageViewShowBalance.visibility = View.GONE
        binding.viewLine.visibility = View.GONE
    }

    private fun hideBalance() {
        preferences.setVisibilityBalance(false)
        binding.imageViewShowBalance.visibility = View.VISIBLE
        binding.viewLine.visibility = View.VISIBLE
        binding.imageViewHideBalance.visibility = View.GONE
        binding.textViewBalanceValue.visibility = View.GONE

    }

    private fun getMyStatement() {
        lifecycleScope.launch {
            viewModel.getStatementPagination().collectLatest {
                adapter.submitData(it.map { item -> item.mapperToStatementList() })
            }
        }
    }

    private fun getMyBalance() {
        viewModel.getBalance().observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    binding.textViewBalanceValue.text =
                        convertCentsToReal(dataState.data.amount).moneyFormat()
                }
                is DataState.Failure -> showToastLong(dataState.message)
                is DataState.Error -> showToastLong(Constants.GENERIC_ERROR)
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewHideBalance -> hideBalance()
            R.id.imageViewShowBalance -> showBalance()
        }
    }
}