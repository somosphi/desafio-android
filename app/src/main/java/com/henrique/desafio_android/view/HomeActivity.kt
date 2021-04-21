package com.henrique.desafio_android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.henrique.desafio_android.BR
import com.henrique.desafio_android.R
import com.henrique.desafio_android.databinding.ActivityHomeBinding
import com.henrique.desafio_android.service.repository.GetBalanceInteractor
import com.henrique.desafio_android.service.repository.GetMyStatementInteractor
import com.henrique.desafio_android.service.loadKoinModules
import com.henrique.desafio_android.view.adapter.MovimentationAdapter
import com.henrique.desafio_android.service.listener.MovimentationListener
import com.henrique.desafio_android.viewmodel.HomeViewModel
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this@HomeActivity
        binding.setVariable(BR.viewModel, mViewModel)

        setupMovimentationList()
        setupListener()
        observe()
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.resume()
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
            }
        ) {
            override fun onListClick(id: String) {
                val intent = Intent(applicationContext, MovimentationDetailActivity::class.java)
                Bundle(1).apply {
                    putString(MovimentationDetailActivity.MOVIMENTATION_ID_EXTRAS, id)
                    intent.putExtras(this)
                }
                startActivity(intent)
            }

        }
    }

    private fun observe() {
        mViewModel.myStatementResponse.observe(this, {
            if (it.items.count() > 0) {
                mAdapter.updateList(it.items.toMutableList())
                mViewModel.offset++
            }
        })
    }

}