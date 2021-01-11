package com.newton.phi.view.fragment

import android.app.ActionBar
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.newton.phi.R
import com.newton.phi.adapter.DetailAdapter
import com.newton.phi.databinding.FragmentReceiptBinding
import com.newton.phi.model.view.ItemDetail
import com.newton.phi.view.viewmodel.TransactionViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ReceiptFragment : Fragment() {

    private lateinit var adapterItem : DetailAdapter
    private lateinit var binding : FragmentReceiptBinding
    private val viewModel : TransactionViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_receipt, container, false
        )

        binding.run {
            lifecycleOwner = this@ReceiptFragment
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterItem = DetailAdapter()
        recyclerDetail()
        viewModel.getItemTransaction()
        observableField()
        binding.button.setOnClickListener { getBitmapFromView(view) }
    }

    private fun observableField() {
        viewModel.requestItemTransaction().observe(viewLifecycleOwner, {
            listTransactions(it)
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

    private fun listTransactions(list: List<ItemDetail>) {
        adapterItem.setListInAdapter(list)
    }

    private fun recyclerDetail(){
        binding.recyclerItem.run {
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = adapterItem
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun getBitmapFromView(v: View) {

            val bit = binding.cardView.drawToBitmap(Bitmap.Config.ARGB_8888)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, bit)
            startActivity(Intent.createChooser(intent, getString(R.string.receipt)))


    }
}