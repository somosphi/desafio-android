package com.example.desafiophi.features.bankStatement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.architecture.android.viewBinding
import com.example.desafiophi.architecture.networking.Resource
import com.example.desafiophi.data.models.responses.Statement
import com.example.desafiophi.databinding.FragmentBankStatementBinding
import com.example.desafiophi.utils.EndlessRecyclerViewScrollListener
import com.example.desafiophi.utils.maskBrazilianCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel


class BankStatementFragment : Fragment(R.layout.fragment_bank_statement) {

    private val binding by viewBinding(FragmentBankStatementBinding::bind)

    private val viewModel by viewModel<BankStatementViewModel>()

    private lateinit var adapter: BankStatementAdapter

    private var isInitialLoading = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureObservers()

        viewModel.getBalance()

        viewModel.getStatement(0)

        setupVisibilityEye()
    }

    private fun setupVisibilityEye() {
        binding.buttonBalanceVisibility.setOnClickListener { visibilityEye ->
            visibilityEye.isSelected = !visibilityEye.isSelected
            binding.textYourBalanceValue.visibility =
                if (visibilityEye.isSelected) View.VISIBLE else View.INVISIBLE
            binding.viewBalance.visibility =
                if (visibilityEye.isSelected) View.INVISIBLE else View.VISIBLE
        }
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
                }
            }
        })

        viewModel.statement.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    if (isInitialLoading) {
                        setupStatementRecyclerView(resource.data!!)
                    } else {
                        adapter.appendToList(resource.data!!)
                    }
                }
                Resource.Status.ERROR -> TODO()
                Resource.Status.LOADING -> {
                }
            }
        })
    }

    private fun setupStatementRecyclerView(list: List<Statement.Item>) {
        adapter = BankStatementAdapter(list.toMutableList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerStatement.layoutManager = layoutManager
        binding.recyclerStatement.adapter = adapter
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                isInitialLoading = false
                viewModel.getStatement(page)
            }
        }
        binding.recyclerStatement.addOnScrollListener(scrollListener)
    }
}