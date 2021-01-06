package com.example.desafiophi.features.bankStatementDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.desafiophi.R
import com.example.desafiophi.architecture.android.viewBinding
import com.example.desafiophi.databinding.FragmentBankStatementDetailBinding

class BankStatementDetailFragment : Fragment(R.layout.fragment_bank_statement_detail) {

    private val binding by viewBinding(FragmentBankStatementDetailBinding::bind)
    private val args: BankStatementDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}