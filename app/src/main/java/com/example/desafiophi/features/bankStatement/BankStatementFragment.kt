package com.example.desafiophi.features.bankStatement

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafiophi.R
import com.example.desafiophi.architecture.networking.Resource

class BankStatementFragment : Fragment(R.layout.fragment_bank_statement) {

    lateinit var viewModel: BankStatementViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(BankStatementViewModel::class.java)

        configureObservers()

        viewModel.getBalance()
    }

    private fun configureObservers() {
        viewModel.balance.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(requireContext(), resource.data, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> TODO()
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}