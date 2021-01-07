package com.example.desafiophi.features.bankStatementDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.desafiophi.R
import com.example.desafiophi.architecture.android.viewBinding
import com.example.desafiophi.architecture.networking.Resource
import com.example.desafiophi.databinding.FragmentBankStatementDetailBinding
import com.example.desafiophi.utils.getBitmap
import com.example.desafiophi.utils.maskBrazilianCurrency
import com.example.desafiophi.utils.saveToCache
import com.example.desafiophi.utils.toBrazilianDateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel


class BankStatementDetailFragment : Fragment(R.layout.fragment_bank_statement_detail) {

    private val binding by viewBinding(FragmentBankStatementDetailBinding::bind)
    private val args: BankStatementDetailFragmentArgs by navArgs()
    private val viewModel by viewModel<BankStatementDetailViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureObservers()
        viewModel.getStatementDetail(args.id)
    }

    private fun configureObservers() {
        viewModel.statementDetail.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressStatementDetail.visibility = View.GONE
                    with(resource.data!!) {
                        binding.textTypeValue.text = this.description
                        binding.textValueValue.text =
                            this.amount.toDouble().maskBrazilianCurrency(true)
                        binding.textRecipientValue.text = this.to
                        binding.textDateValue.text = this.createdAt.toBrazilianDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss",
                            "dd/MM/yyyy - hh:mm:ss"
                        )
                        binding.textAuthenticationValue.text = this.authentication
                    }
                    binding.buttonShare.setOnClickListener {
                        shareReceipt()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressStatementDetail.visibility = View.GONE
                    Toast.makeText(
                        requireContext(), getString(R.string.generic_network_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Resource.Status.LOADING -> {
                    binding.progressStatementDetail.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun shareReceipt() {
        val bitmap = binding.detailContainer.getBitmap()
        val imageUri = bitmap.saveToCache(requireContext())
        if (imageUri != null) {
            val shareIntent = setupShareIntent(imageUri)
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }

    private fun setupShareIntent(imageUri: Uri): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(
                imageUri,
                requireActivity().contentResolver.getType(imageUri)
            )
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
        }
    }
}
