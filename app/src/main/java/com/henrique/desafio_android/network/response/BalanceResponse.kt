package com.henrique.desafio_android.network.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BalanceResponse(@SerializedName("amount") val amount: BigDecimal)
