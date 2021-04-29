package com.example.pedroneryphi.di

import com.example.pedroneryphi.viewmodel.DetailViewModel
import com.example.pedroneryphi.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { MainViewModel(get()) }

    viewModel { DetailViewModel(get()) }


}