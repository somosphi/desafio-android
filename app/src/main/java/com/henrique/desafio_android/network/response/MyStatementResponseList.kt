package com.henrique.desafio_android.network.response

import com.google.gson.annotations.SerializedName

data class MyStatementResponseList(
    @SerializedName("items") val items: List<MyStatementResponse>
)
