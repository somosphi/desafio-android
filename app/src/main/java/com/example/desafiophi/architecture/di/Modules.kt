package com.example.desafiophi.architecture.di

import com.example.desafiophi.data.PhiAPI
import com.example.desafiophi.features.bankStatement.BankStatementViewModel
import com.example.desafiophi.features.bankStatementDetail.BankStatementDetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { BankStatementViewModel(get()) }
    viewModel { BankStatementDetailViewModel(get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { providePhiApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://desafio-mobile-bff.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun providePhiApi(retrofit: Retrofit): PhiAPI =
    retrofit.create(PhiAPI::class.java)