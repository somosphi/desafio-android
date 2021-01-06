package com.example.desafiophi.architecture.di

import com.example.desafiophi.features.bankStatement.BankStatementViewModel
import com.example.desafiophi.features.bankStatementDetail.BankStatementDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val phiModule = module {
    viewModel { BankStatementViewModel() }
    viewModel { BankStatementDetailViewModel() }
}