package com.ipsoft.ph.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ipsoft.ph.R
import com.ipsoft.ph.databinding.ActivityCheckingCopyAcitivityBinding
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.viewmodel.MainViewModel

class CheckingCopyAcitivity : AppCompatActivity() {
    private lateinit var checkingBinding: ActivityCheckingCopyAcitivityBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingBinding = ActivityCheckingCopyAcitivityBinding.inflate(layoutInflater)
        val view = checkingBinding.root
        setContentView(view)


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {

            title = ""
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }

        val id = intent.getStringExtra("id") ?: ""

        initViewModel()


        viewModel.getDetailTransaction(id).observe(this, Observer {
            setValues(it)
        })


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setValues(transaction: Transaction) {

        if (transaction != null) {

            checkingBinding.textAuth.text = transaction.authentication
            checkingBinding.textViewTransactionType.text = transaction.tType
            checkingBinding.textReceiver.text = transaction.sender
            checkingBinding.textViewValue.text =
                "R$ ${transaction.amount.toString().replace(".", ",")}"
            checkingBinding.textdate.text = transaction.createdAd
        } else {
            Toast.makeText(this, getString(R.string.transaction_info_error), Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

    }

}