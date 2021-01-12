package br.com.phi.challenge.api.statement.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Created by pcamilo on 10/01/2021.
 */
data class StatementItemResponse(
    @SerializedName("id") val id: String,
    @SerializedName("amount") val amount: BigDecimal,
    @SerializedName("tType") val statementType: String,
    @SerializedName("description") val description: String,
    @SerializedName("to") val destiny: String? = null,
    @SerializedName("from") val from: String? = null,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("authentication") val authentication: String? = null)