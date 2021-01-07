package me.luanhenriquer8.phitest.ui.home

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.luanhenriquer8.phitest.R
import me.luanhenriquer8.phitest.adapters.TransactionsAdapter
import me.luanhenriquer8.phitest.data.api.ApiService
import me.luanhenriquer8.phitest.data.models.Statement
import me.luanhenriquer8.phitest.data.models.Transaction
import me.luanhenriquer8.phitest.databinding.ActivityMainBinding
import me.luanhenriquer8.phitest.utils.BALANCE_FILE_NAME
import me.luanhenriquer8.phitest.utils.MUST_SHOW_MY_BALANCE
import me.luanhenriquer8.phitest.utils.SIZE_REQUEST_LIST
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var incrementStatementListBR: IncrementStatementListBR
    private lateinit var sharedPreferences: SharedPreferences
    private var mustShowMyBalance = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.lifecycleOwner = this

        createSlideDownLoader()
        initializeObservers()
        initIncrementStatementListBR()
        initVariables()
    }

    override fun onResume() {
        super.onResume()
        createEmptyState()
        fetchHomeData()
        setToggleOperation(mustShowMyBalance)
    }

    private fun initVariables() {
        sharedPreferences = getSharedPreferences(BALANCE_FILE_NAME, MODE_PRIVATE)
        mustShowMyBalance = sharedPreferences.getBoolean(MUST_SHOW_MY_BALANCE, false)
    }

    private fun initIncrementStatementListBR() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(getString(R.string.increment_statement_list))
        incrementStatementListBR = IncrementStatementListBR()
        registerReceiver(incrementStatementListBR, intentFilter)
    }

    private fun initializeObservers() {
        viewModel.myBalance.observe(this, {
            if (it != null) binding.amountText.text = it.extractAmountAsCurrency()
        })

        viewModel.myStatementList.observe(this, {
            if (it.isNotEmpty()) createMyStatementList(it)
        })

        viewModel.operationFailed.observe(this, {
            if (it) showErrorMessage()
        })

        binding.balanceToggle.setOnClickListener {
            mustShowMyBalance = !mustShowMyBalance
            setToggleOperation(mustShowMyBalance)
        }
    }

    private fun setToggleOperation(mustShowMyBalance: Boolean) {
        showOrHideMyBalance(mustShowMyBalance)
        changeToggleDrawable(mustShowMyBalance)
        sharedPreferences.edit().putBoolean(MUST_SHOW_MY_BALANCE, mustShowMyBalance).apply()
    }

    private fun changeToggleDrawable(mustShowMyBalance: Boolean) {
        binding.balanceToggle.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (mustShowMyBalance) R.drawable.ic_eye_visible else R.drawable.ic_eye_invisible
            )
        )
    }

    private fun showOrHideMyBalance(mustShowMyBalance: Boolean) {
        binding.amountText.visibility = if (mustShowMyBalance) View.VISIBLE else View.INVISIBLE
        binding.lineForBalanceInvisible.visibility =
            if (mustShowMyBalance) View.INVISIBLE else View.VISIBLE
    }

    private fun createEmptyState() {
        binding.amountText.text = getString(R.string.loading)
        createMyStatementList(arrayListOf())
    }

    private fun createMyStatementList(list: ArrayList<Statement>) {
        binding.transactionsRecyclerView.apply {
            adapter = TransactionsAdapter(this@MainActivity, list)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun createSlideDownLoader() {
        binding.swipeContainer.setOnRefreshListener {
            createEmptyState()
            Handler().postDelayed({
                fetchHomeData()
                binding.swipeContainer.isRefreshing = false
            }, 2000)
        }
    }

    private fun fetchHomeData() {
        viewModel.fetchMyBalance()
        viewModel.fetchStatement()
    }

    private inner class IncrementStatementListBR : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val offset =
                (viewModel.myStatementList.value?.size?.div(SIZE_REQUEST_LIST) ?: 1).plus(1)

            ApiService.statement().get(offset).enqueue(object : Callback<Transaction> {
                override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                    if (response.isSuccessful) {
                        val adapter =
                            binding.transactionsRecyclerView.adapter as TransactionsAdapter
                        response.body()?.items?.let { adapter.addItems(it) }
                    }
                }

                override fun onFailure(call: Call<Transaction>, t: Throwable) {
                    showErrorMessage()
                }
            })
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.operation_failed_try_again),
            Toast.LENGTH_LONG
        ).show()
    }
}