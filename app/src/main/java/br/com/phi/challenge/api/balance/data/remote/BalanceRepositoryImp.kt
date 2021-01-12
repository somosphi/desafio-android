package br.com.phi.challenge.api.balance.data.remote

import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.balance.data.model.BalanceResponse
import br.com.phi.challenge.api.balance.domain.BalanceRepository
import br.com.phi.challenge.api.executeApi

/**
 * Created by pcamilo on 10/01/2021.
 */
class BalanceRepositoryImp(private val balanceService: BalanceService): BalanceRepository {
    override suspend fun getBalance(): Result<BalanceResponse> = executeApi { balanceService.getBalance() }
}