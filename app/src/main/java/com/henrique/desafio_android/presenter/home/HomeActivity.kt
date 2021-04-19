package com.henrique.desafio_android.presenter.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.henrique.desafio_android.BR
import com.henrique.desafio_android.R
import com.henrique.desafio_android.databinding.ActivityHomeBinding
import com.henrique.desafio_android.domain.home.GetBalanceInteractor
import com.henrique.desafio_android.loadKoinModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity() {

    private val balanceInteractor: GetBalanceInteractor by inject {
        parametersOf()
    }

    private val mViewModel: HomeViewModel by inject {
        parametersOf(balanceInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@HomeActivity)
            loadKoinModules()
        }

        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).apply {
            lifecycleOwner = this@HomeActivity
            setVariable(BR.viewModel, mViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.resume()
    }

}