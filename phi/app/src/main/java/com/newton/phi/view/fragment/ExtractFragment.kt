package com.newton.phi.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.newton.phi.R
import com.newton.phi.adapter.TransactionsAdapter
import com.newton.phi.databinding.FragmentExtractBinding
import com.newton.phi.model.view.Transaction


class ExtractFragment : Fragment() {

    private lateinit var adapterTransaction : TransactionsAdapter
    private lateinit var binding: FragmentExtractBinding

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
            //viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterTransaction = TransactionsAdapter (::recyclerItemClick)
        recyclerViewExtract()

        val list = ArrayList<Transaction>()
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))
        list.add(Transaction("teste","newton","R$ 50.00",false,"10/08"))
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))
        list.add(Transaction("teste","newton","R$ 50.00",false,"10/08"))
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))
        list.add(Transaction("teste","newton","R$ 50.00",false,"10/08"))
        list.add(Transaction("teste","newton","R$ 50.00",true,"10/11"))

        listTransactions(list)
    }

    private fun observableFields() {
//        viewModel.navigationInstallmentsTransaction().observe(viewLifecycleOwner, Observer {
//            findNavController().navigate(R.id.action_installmentsFragment_to_transactionFragment)
//        })
    }

    private fun listTransactions(list : List<Transaction>) {
        adapterTransaction.setListInAdapter(list)
    }

    private fun recyclerViewExtract() {
        binding.recyclerTransactions.run {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = adapterTransaction
//            addItemDecoration(
//                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
//            )
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun recyclerItemClick(position : Int) {
        //viewModel.setInstallments(position)
    }

}