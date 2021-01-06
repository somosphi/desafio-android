package com.example.desafiophi.architecture.di

import com.example.desafiophi.features.bankStatement.BankStatementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val phiModule = module {
    viewModel { BankStatementViewModel() }
}