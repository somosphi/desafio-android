package com.example.phitest

import com.example.phitest.interfaces.TransactionService
import com.example.phitest.repository.TransactionDetailRepository
import com.example.phitest.view.viewmodel.TransactionDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL_BASE = "https://desafio-mobile-bff.herokuapp.com/"

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<TransactionService> { get<Retrofit>().create(TransactionService::class.java) }
}

val repositoryModule = module {
    single { TransactionDetailRepository(get()) }
}

val viewModelModule = module {
    viewModel { TransactionDetailViewModel(get()) }
}

val appModules = listOf(
    retrofitModule, repositoryModule, viewModelModule
)
