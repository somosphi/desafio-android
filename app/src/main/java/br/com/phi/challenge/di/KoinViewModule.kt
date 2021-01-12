package br.com.phi.challenge.di

import br.com.phi.challenge.viewmodel.base.toolbar.ToolbarViewModel
import br.com.phi.challenge.viewmodel.splash.SplashViewModel
import br.com.phi.challenge.viewmodel.statement.StatementViewModel
import br.com.phi.challenge.viewmodel.statementdetail.StatementDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by pcamilo on 10/01/2021.
 */
var viewModelModule = module {
    viewModel { StatementViewModel(get(), get(), get()) }
    viewModel { StatementDetailViewModel(get()) }
    viewModel { SplashViewModel() }
    viewModel { ToolbarViewModel(get()) }
}