package com.chavesdev.phiapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.R
import com.chavesdev.phiapp.databinding.ActivityBalanceAndStatementsBinding
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import javax.inject.Inject

class BalanceAndStatementsActivity : AppCompatActivity(), ViewModelStoreOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding : ActivityBalanceAndStatementsBinding
    lateinit var balanceViewModel : BalanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_and_statements)
        binding.lifecycleOwner = this

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        balanceViewModel = ViewModelProvider(this, viewModelFactory).get(BalanceViewModel::class.java)

        binding.balance = balanceViewModel

        balanceViewModel.loadBalance()

    }
}