package com.newton.phi.di

import com.newton.phi.network.Interector
import com.newton.phi.network.RetrofitInicializer
import com.newton.phi.view.viewmodel.TransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleViewModel = module {
    viewModel { TransactionViewModel(get(),androidContext()) }
}

val moduleInicializerRequest = module {
    factory { RetrofitInicializer() }
}

val moduleInterector = module {
    factory { Interector(get()) }
}

