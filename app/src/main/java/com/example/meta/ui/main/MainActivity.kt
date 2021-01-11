package com.example.meta.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meta.*
import com.example.meta.data.model.AmountResponse
import com.example.meta.data.model.DetailsResponse
import com.example.meta.data.model.ListItemsResponse
import com.example.meta.source.remote.Api
import com.example.meta.source.remote.Api.ServiceBuilder.getRetrofitInstance
import com.example.meta.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import com.example.meta.data.model.ItemsResponse
import com.example.meta.data.repository.main.MainRepositoryImpl
import com.example.meta.extensions.formatCurrency

class MainActivity : AppCompatActivity() {

    var offSet = 0
    var loadInitial = true

    var listaItems =  ArrayList<ListItemsResponse>()
    lateinit var adapterLista: RecyclerAdapter

    companion object {
        const val LIMIT = 10
        const val OFFSET = 1
        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val PREFENRECE_KEY_AMOUNT = "showAmount"
        const val BUNDLE_KEY_DETAILS = "detailsResponse"

        const val REQUEST_ERROR = "Algo de errado aconteceu, tente novamente mais tarde!"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitClient = getRetrofitInstance()
        val endpoint = retrofitClient.create(Api::class.java)
        val repository = MainRepositoryImpl(endpoint)

        viewModel = MainViewModel(repository)

        configureRecyclerView()
        handleAmountVisibily()
        setupObserving()
        callGetAmount()
        callGetStatement()
    }

    private fun handleAmountVisibily() {
        val sharedPreference= getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

        if (sharedPreference.getBoolean(PREFENRECE_KEY_AMOUNT, false)) {
            showAmount()
        } else {
            hideAmount()
        }

        imageShowHideAmount.setOnClickListener {
            if (sharedPreference.getBoolean(PREFENRECE_KEY_AMOUNT, false)) {
                hideAmount()
            } else {
                showAmount()
            }
        }
    }

    private fun setListItems() {
        adapterLista = RecyclerAdapter(listaItems, baseContext) { id ->
            onClick(id)
        }

        recyclerView.adapter = adapterLista

        if (!loadInitial) {
            recyclerView.scrollToPosition(listaItems.size - 1)
        }

        loadInitial = false
    }

    private fun showAmount() {
        imageShowHideAmount.setImageResource(R.mipmap.eye_blue_off)
        viewWithoutAmount.visibility = INVISIBLE
        textViewAmount.visibility = VISIBLE

        val sharedPreference =  getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(PREFENRECE_KEY_AMOUNT, true)
        editor.apply()
    }

    private fun hideAmount() {
        imageShowHideAmount.setImageResource(R.mipmap.eye_blue)
        viewWithoutAmount.visibility = VISIBLE
        textViewAmount.visibility = INVISIBLE

        val sharedPreference =  getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(PREFENRECE_KEY_AMOUNT, false)
        editor.apply()
    }

    private fun configureRecyclerView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(false)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    callGetStatement()
                }
            }
        })
    }

    private fun callGetAmount() {
        viewModel.loadAmount(TOKEN)
    }

    private fun callGetStatement() {
        viewModel.loadStatement(TOKEN, LIMIT, offSet)
        offSet+= OFFSET
    }

    private fun setupObserving() {
        viewModel.states.observe(this, Observer {state ->
            when(state) {
                is MainViewState.ShowAmount -> showAmountValue(state.amountResponse)
                is MainViewState.ShowStatement -> showItemsStatement(state.itemsResponse)
                is MainViewState.ShowStatementDetails -> goToDetailsActivity(state.detailsResponse)
                is MainViewState.ShowError -> showError(state.isRequestError)
                is MainViewState.ShowLoading -> showLoading(state.isLoading)
            }
        })
    }

    private fun showAmountValue(amountResponse: AmountResponse) {
        textViewAmount?.text = formatCurrency(amountResponse.amount)
    }

    private fun showItemsStatement(itemsResponse: ItemsResponse) {
        for (item in itemsResponse.items) {
            listaItems.add(item)
        }

        setListItems()
    }

    private fun showError(isRequestError: Boolean) {
        if (isRequestError) {
            Toast.makeText(this, REQUEST_ERROR, Toast.LENGTH_LONG)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.visibility = VISIBLE
        } else {
            loading.visibility = GONE
        }
    }

    private fun goToDetailsActivity(detailsResponse: DetailsResponse) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(BUNDLE_KEY_DETAILS, detailsResponse)
        startActivity(intent)
    }

    private fun onClick(id: String) {
        viewModel.loadStatementDetails(TOKEN, id)
    }
}