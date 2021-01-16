package me.luanhenriquer8.phitest.di

import me.luanhenriquer8.phitest.data.api.ApiService
import me.luanhenriquer8.phitest.ui.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { ApiService() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}