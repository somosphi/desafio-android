package br.com.andreviana.phi.desafioandroid.di

import br.com.andreviana.phi.desafioandroid.data.network.source.StatementDataSource
import br.com.andreviana.phi.desafioandroid.data.network.source.StatementDataSourceImpl
import br.com.andreviana.phi.desafioandroid.data.repository.StatementRepository
import br.com.andreviana.phi.desafioandroid.data.repository.StatementRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindStatementRepository(
            statementRepositoryImpl: StatementRepositoryImpl
    ): StatementRepository

    @Singleton
    @Binds
    abstract fun bindStatementDataSource(
            statementDataSourceImpl: StatementDataSourceImpl
    ): StatementDataSource
}