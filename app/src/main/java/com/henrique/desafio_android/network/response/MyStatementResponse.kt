package com.henrique.desafio_android.network.response

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MyStatementResponse(
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("id") val id: String,
    @SerializedName("amount") val amount: BigDecimal,
    @SerializedName("to") val to: String?,
    @SerializedName("from") val from: String?,
    @SerializedName("description") val description: String,
    @SerializedName("tType") val tType: String,
    @SerializedName("bankName") val bankName: String?,
    @SerializedName("authentication") val authentication: String?
)