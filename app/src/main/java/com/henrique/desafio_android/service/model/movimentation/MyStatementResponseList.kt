package com.henrique.desafio_android.service.model.movimentation

import com.google.gson.annotations.SerializedName

data class MyStatementResponseList(
    @SerializedName("items") val items: List<MyStatementResponse>
)
