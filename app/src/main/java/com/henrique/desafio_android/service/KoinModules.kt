package com.henrique.desafio_android.service

import com.henrique.desafio_android.service.repository.GetBalanceInteractor
import com.henrique.desafio_android.service.repository.GetMyStatementInteractor
import com.henrique.desafio_android.service.repository.remote.API
import com.henrique.desafio_android.service.repository.RemoteDataSource
import com.henrique.desafio_android.service.repository.Repository
import com.henrique.desafio_android.service.repository.remote.RetrofitClient
import com.henrique.desafio_android.viewmodel.HomeViewModel
import com.henrique.desafio_android.viewmodel.MovimentationDetailViewModel
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
        GetMyStatementInteractor(
            repository = get(),
            resources = androidContext().resources
        )
    }
}

private val viewModelModule = module {
    viewModel { (balanceInteractor: GetBalanceInteractor, myStatementInteractor: GetMyStatementInteractor) ->
        HomeViewModel(
            balanceInteractor = balanceInteractor,
            myStatementInteractor = myStatementInteractor
        )
    }
    viewModel { (myStatementInteractor: GetMyStatementInteractor) ->
        MovimentationDetailViewModel(myStatementInteractor)
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