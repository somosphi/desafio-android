package br.com.phi.challenge.api.balance.domain

import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.balance.data.model.BalanceResponse

/**
 * Created by pcamilo on 10/01/2021.
 */
interface BalanceRepository {

    /**
     * Get the current balance
     */
    suspend fun getBalance(): Result<BalanceResponse>
}