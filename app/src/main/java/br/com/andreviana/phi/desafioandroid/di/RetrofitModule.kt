package br.com.andreviana.phi.desafioandroid.di

import br.com.andreviana.phi.desafioandroid.data.network.Endpoints.BASE_URL
import br.com.andreviana.phi.desafioandroid.data.network.service.StatementService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultRetrofit

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    @DefaultClient
    fun provideDefaultClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    @DefaultRetrofit
    fun provideDefaultRetrofit(@DefaultClient client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
    }

    @Singleton
    @Provides
    fun provideStatementService(@DefaultRetrofit retrofit: Retrofit.Builder): StatementService {
        return retrofit.build().create(StatementService::class.java)
    }
}