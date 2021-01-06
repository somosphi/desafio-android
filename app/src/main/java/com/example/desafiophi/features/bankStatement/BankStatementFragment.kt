package com.example.desafiophi.features.bankStatement

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.desafiophi.R
import com.example.desafiophi.architecture.android.viewBinding
import com.example.desafiophi.architecture.networking.Resource
import com.example.desafiophi.databinding.FragmentBankStatementBinding
import com.example.desafiophi.utils.maskBrazilianCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel

class BankStatementFragment : Fragment(R.layout.fragment_bank_statement) {

    private val binding by viewBinding(FragmentBankStatementBinding::bind)

    private val viewModel by viewModel<BankStatementViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureObservers()

        viewModel.getBalance()
    }

    private fun configureObservers() {
        viewModel.balance.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.textYourBalanceValue.text =
                        resource.data?.toDouble()?.maskBrazilianCurrency(true)
                }
                Resource.Status.ERROR -> TODO()
                Resource.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}