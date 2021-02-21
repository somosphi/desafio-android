package com.chavesdev.phiapp.views.statement_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.R
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.views.StatementsViewModel
import javax.inject.Inject

class StatementDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var statementsViewModel: StatementsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        statementsViewModel =
            ViewModelProvider(this, viewModelFactory).get(StatementsViewModel::class.java)

        intent.getStringExtra(statementId)?.let {
            statementsViewModel.getStatementDetail(it)
        }

        statementsViewModel.statementDetail.observe(this, {
            populateDetails()
        })
    }

    private fun populateDetails() {
        Toast.makeText(this, statementsViewModel.statementDetail.value?.authentication, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val statementId = "statement.id"
    }
}