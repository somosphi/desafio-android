package com.newton.phi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.newton.phi.R
import com.newton.phi.adapter.TransactionsAdapter
import com.newton.phi.databinding.FragmentExtractBinding
import com.newton.phi.model.view.Transaction
import com.newton.phi.view.viewmodel.TransactionViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ExtractFragment : Fragment() {

    private lateinit var adapterTransaction : TransactionsAdapter
    private lateinit var binding: FragmentExtractBinding
    private val viewModel : TransactionViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_extract, container, false
        )

        binding.run {
            lifecycleOwner = this@ExtractFragment
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterTransaction = TransactionsAdapter(::recyclerItemClick)
        recyclerViewExtract()
        viewModel.getBalanceView()
        viewModel.getTransactions()
        observableFields()
    }

    private fun observableFields() {
        viewModel.requestListTransaction().observe(viewLifecycleOwner, {
            listTransactions(it)
        })
        viewModel.alertInternet().observe(viewLifecycleOwner, {
            if (it){
                informInternet()
            }
        })
        viewModel.alertError().observe(viewLifecycleOwner, {
            if (it){
                alertError()
            }
        })
    }

    private fun alertError() {
        val snackbar = Snackbar.make(binding.root, getString(R.string.dialog_error), Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun informInternet(){
        val snackbar = Snackbar.make(binding.root, getString(R.string.without_internet), Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun listTransactions(list: List<Transaction>) {
        adapterTransaction.setListInAdapter(list)
    }

    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItens = 0
    private var viewThreshold = 10

    private fun recyclerViewExtract() {
        binding.recyclerTransactions.run {
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = adapterTransaction
            itemAnimator = DefaultItemAnimator()

            addOnScrollListener(object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy>0){

                        visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                        totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                        pastVisibleItens = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        if ((totalItemCount+visibleItemCount)<=(pastVisibleItens+viewThreshold)){
                            viewModel.getTransactions()
                        }
                    }
                }
            })
        }


    }

    private fun recyclerItemClick(position: String) {
        viewModel.setKeyId(position)
        findNavController().navigate(R.id.action_extractFragment_to_receiptFragment)
    }

}