package com.ipsoft.ph.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ipsoft.ph.R
import com.ipsoft.ph.databinding.ActivityCheckingCopyAcitivityBinding
import com.ipsoft.ph.repository.model.Transaction

class CheckingCopyAcitivity : AppCompatActivity() {
    private lateinit var checkingBinding: ActivityCheckingCopyAcitivityBinding
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

        setValues()





    }

    override fun onResume() {
        super.onResume()
        setValues()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setValues() {
        var transaction = intent.getParcelableExtra<Transaction>("transaction")

        if(transaction != null) {

            checkingBinding.textAuth.text = transaction.authentication
            checkingBinding.textViewTransactionType.text = transaction.tType
            checkingBinding.textReceiver.text = transaction.sender
            checkingBinding.textViewValue.text = "R$ ${transaction.amount.toString().replace(".",",")}"
            checkingBinding.textdate.text = transaction.createdAd
        }else {
            Toast.makeText(this,"Erro ao opter dados da transação", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}