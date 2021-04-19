package com.henrique.desafio_android

import com.henrique.desafio_android.domain.home.GetBalanceInteractor
import com.henrique.desafio_android.domain.home.GetMyStatementInteractor
import com.henrique.desafio_android.network.API
import com.henrique.desafio_android.network.RemoteDataSource
import com.henrique.desafio_android.network.Repository
import com.henrique.desafio_android.network.RetrofitClient
import com.henrique.desafio_android.presenter.home.HomeViewModel
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
}

private val networkModule = module {
    factory<Repository> {
        RemoteDataSource(api = get())
    }

    single(override = true) {
        RetrofitClient.createService(API::class.java)
    }
}