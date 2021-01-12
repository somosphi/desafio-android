package br.com.phi.challenge.di

import android.content.Context
import br.com.phi.challenge.BuildConfig
import br.com.phi.challenge.api.AuthInterceptor
import br.com.phi.challenge.api.balance.data.local.BalancePreferences
import br.com.phi.challenge.api.balance.data.remote.BalanceRepositoryImp
import br.com.phi.challenge.api.balance.data.remote.BalanceService
import br.com.phi.challenge.api.statement.data.remote.StatementRepositoryImp
import br.com.phi.challenge.api.statement.data.remote.StatementService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by pcamilo on 10/01/2021.
 */

const val BALANCE_PREF = "BALANCE_PREF"

val repositoryModule = module {

   single { BalancePreferences() }
   single(named(BALANCE_PREF)) { androidApplication().getSharedPreferences(BALANCE_PREF, Context.MODE_PRIVATE) }

    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get(), get()) }

    single { provideLoggingCapableHttpClient() }
    single { provideRetrofit(get()) }

    // Balance services
    factory { provideBalanceService(get()) }
    single { BalanceRepositoryImp(get()) }

    // Statement services
    factory { provideStatementService(get()) }
    single { StatementRepositoryImp(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.APP_BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(Gson())).build()

fun provideOkHttpClient(authInterceptor: AuthInterceptor, logInterceptor: HttpLoggingInterceptor) =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).addInterceptor(logInterceptor).build()

fun provideBalanceService(retrofit: Retrofit) : BalanceService = retrofit.create(BalanceService::class.java)
fun provideStatementService(retrofit: Retrofit) : StatementService = retrofit.create(StatementService::class.java)

fun provideLoggingCapableHttpClient(): HttpLoggingInterceptor {
    val httpLogging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) httpLogging.level = HttpLoggingInterceptor.Level.BODY
    return httpLogging
}