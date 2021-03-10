package br.com.andreviana.phi.desafioandroid.ui.statement.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.common.DataState
import br.com.andreviana.phi.desafioandroid.databinding.ActivityExtractBinding
import br.com.andreviana.phi.desafioandroid.util.helper.PreferencesHelper
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat
import br.com.andreviana.phi.desafioandroid.util.ktx.navigationToStatementDetail
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ExtractActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: ExtractViewModel by viewModels()
    private val binding by lazy { ActivityExtractBinding.inflate(layoutInflater) }
    private val preferences: PreferencesHelper by lazy { PreferencesHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getMyStatement()
        getMyBalance()
        setupUI()
    }

    private fun setupUI() {
        binding.swipeRefreshMoves.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.teal_custom_300
            )
        )
        binding.imageViewHideBalance.setOnClickListener(this)
        binding.imageViewShowBalance.setOnClickListener(this)
        checkVisibilityBalance()
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

    private fun checkVisibilityBalance() {
        if (preferences.getVisibilityBalance()) showBalance()
        else hideBalance()
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
        viewModel.getStatement("10", "0").observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    Timber.tag(TAG).i("Loading")
                }

                is DataState.Success -> {
                    Timber.tag(TAG).i("Sucesso: ${dataState.data}")
                    binding.recyclerViewMoves.adapter = ExtractAdapter(dataState.data.items) {
                        navigationToStatementDetail(it)
                    }
                }

                is DataState.Failure -> {
                    Timber.tag(TAG).i("Falha: ${dataState.message} e cod: ${dataState.code}")
                }

                is DataState.Error -> {
                    Timber.tag(TAG).i("Error: ${dataState.throwable.message}")
                }
            }
        })
    }

    private fun getMyBalance() {
        viewModel.getBalance().observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    Timber.tag(TAG).i("Loading")
                }

                is DataState.Success -> {
                    Timber.tag(TAG).i("Sucesso: ${dataState.data}")
                    binding.textViewBalanceValue.text =
                        convertCentsToReal(dataState.data.amount).moneyFormat()
                }

                is DataState.Failure -> {
                    Timber.tag(TAG).i("Falha: ${dataState.message} e cod: ${dataState.code}")
                }

                is DataState.Error -> {
                    Timber.tag(TAG).i("Error: ${dataState.throwable.message}")
                }
            }
        })
    }

    companion object {
        private const val TAG = "ExtractActivity"
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewHideBalance -> hideBalance()
            R.id.imageViewShowBalance -> showBalance()
        }
    }
}