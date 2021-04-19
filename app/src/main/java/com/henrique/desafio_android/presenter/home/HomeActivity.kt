package com.henrique.desafio_android.presenter.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.henrique.desafio_android.BR
import com.henrique.desafio_android.R
import com.henrique.desafio_android.databinding.ActivityHomeBinding
import com.henrique.desafio_android.domain.home.GetBalanceInteractor
import com.henrique.desafio_android.domain.home.GetMyStatementInteractor
import com.henrique.desafio_android.loadKoinModules
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.presenter.movimentation.MovimentationAdapter
import com.henrique.desafio_android.presenter.movimentation.MovimentationListener
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mListener: MovimentationListener

    private val mAdapter by lazy {
        MovimentationAdapter(this)
    }

    private val balanceInteractor: GetBalanceInteractor by inject {
        parametersOf()
    }

    private val myStatementInteractor: GetMyStatementInteractor by inject {
        parametersOf()
    }

    private val mViewModel: HomeViewModel by inject {
        parametersOf(balanceInteractor, myStatementInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@HomeActivity)
            loadKoinModules()
        }

        binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        binding.lifecycleOwner = this@HomeActivity
        binding.setVariable(BR.viewModel, mViewModel)

        setupMovimentationList()
        setupListener()
        observe()
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.getBalance()
        mViewModel.getMyStatement()
    }

    private fun setupMovimentationList() {
        binding.homeMovimentationList.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = mAdapter
        }
    }

    private fun setupListener() {
        mListener = getListener()
        mListener.let {
            binding.homeMovimentationList.addOnScrollListener(it)
        }
    }

    private fun getListener(): MovimentationListener {
        return object : MovimentationListener(
            binding.homeMovimentationList.layoutManager as LinearLayoutManager,
            {
                mViewModel.getMyStatement()
            },
            mViewModel.offset
        ) {
            override fun onListClick(movimentationResponse: MyStatementResponse) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun observe() {
        mViewModel.myStatementResponse.observe(this, {
            mAdapter.updateList(it.items.toMutableList())
        })
    }

}