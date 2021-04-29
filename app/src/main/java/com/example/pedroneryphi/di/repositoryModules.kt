package com.example.pedroneryphi.di

import com.example.pedroneryphi.repository.DetailRepository
import com.example.pedroneryphi.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { MainRepository(get()) }

    single { DetailRepository(get(), get()) }
}