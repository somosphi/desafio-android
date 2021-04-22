package com.henrique.desafio_android.di

import com.henrique.desafio_android.domain.repository.GetBalanceInteractor
import com.henrique.desafio_android.domain.repository.GetMovimentationInteractor
import com.henrique.desafio_android.domain.repository.remote.API
import com.henrique.desafio_android.domain.repository.RemoteDataSource
import com.henrique.desafio_android.domain.repository.Repository
import com.henrique.desafio_android.domain.repository.remote.RetrofitClient
import com.henrique.desafio_android.viewmodel.home.HomeViewModel
import com.henrique.desafio_android.viewmodel.movimentationdetail.MovimentationDetailViewModel
import org.koin.core.context.loadKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun loadKoinModules() {
    loadKoinModules(
        listOf(interactorModule, networkModule, viewModelModule)
    )
}

private val interactorModule = module {
    factory {
        GetBalanceInteractor(
            repository = get(),
            resources = androidContext().resources
        )
    }
    factory {
        GetMovimentationInteractor(
            repository = get(),
            resources = androidContext().resources
        )
    }
}

private val viewModelModule = module {
    viewModel { (balanceInteractor: GetBalanceInteractor, movimentationInteractor: GetMovimentationInteractor) ->
        HomeViewModel(
            balanceInteractor = balanceInteractor,
            movimentationInteractor = movimentationInteractor
        )
    }
    viewModel { (movimentationInteractor: GetMovimentationInteractor) ->
        MovimentationDetailViewModel(movimentationInteractor)
    }
}

private val networkModule = module {
    factory<Repository> {
        RemoteDataSource(api = get())
    }

    single(override = true) {
        RetrofitClient.createService(API::class.java)
    }
}