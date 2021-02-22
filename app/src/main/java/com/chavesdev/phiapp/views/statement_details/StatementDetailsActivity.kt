package com.chavesdev.phiapp.views.statement_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.R
import com.chavesdev.phiapp.databinding.ActivityStatementDetailsBinding
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.util.format
import javax.inject.Inject

class StatementDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityStatementDetailsBinding
    private lateinit var statementDetailsViewModel: StatementDetailViewModel
    private lateinit var detailsViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statement_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        statementDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(StatementDetailViewModel::class.java)
        detailsViewModel = DetailViewModel()
        binding.details = detailsViewModel
        binding.lifecycleOwner = this

        statementDetailsViewModel.details.observe(this, Observer {
            detailsViewModel.parseMessage(it)
        })

        intent.getStringExtra(statementId)?.let {
            statementDetailsViewModel.getDetails(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        const val statementId = "statement.id"
    }

    class DetailViewModel(
        val type: MutableLiveData<String>,
        val amount: MutableLiveData<String>,
        val receiver: MutableLiveData<String>,
        val bankName: MutableLiveData<String>,
        val datetime: MutableLiveData<String>,
        val auth: MutableLiveData<String>
    ) : ViewModel() {
        constructor() : this(MutableLiveData(""), MutableLiveData(""), MutableLiveData(""), MutableLiveData(""), MutableLiveData(""), MutableLiveData(""))

        fun parseMessage(message: StatementMessage?) {
            message?.let {
                type.postValue(it.description)
                amount.postValue(it.formatedNumber())
                receiver.postValue(it.getOrigin())
                datetime.postValue(it.date.format("dd/MM - HH:mm:ss"))
                bankName.postValue(it.bankName)
                auth.postValue(it.authentication)
            }
        }
    }
}