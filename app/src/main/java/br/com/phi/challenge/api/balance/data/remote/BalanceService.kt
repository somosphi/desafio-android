package br.com.phi.challenge.api.balance.data.remote

import br.com.phi.challenge.api.balance.data.model.BalanceResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by pcamilo on 10/01/2021.
 */
interface BalanceService {

    /**
     * Get the current balance
     */
    @GET("myBalance")
    suspend fun getBalance(): Response<BalanceResponse>
}