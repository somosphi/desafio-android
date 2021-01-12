package br.com.phi.challenge.api.statement.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pcamilo on 10/01/2021.
 */
data class StatementResponse(
    @SerializedName("items") val items: ArrayList<StatementItemResponse>
)