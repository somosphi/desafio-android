package br.com.phi.challenge.api.balance.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Created by pcamilo on 10/01/2021.
 */
data class BalanceResponse(@SerializedName("amount") val amount: BigDecimal)