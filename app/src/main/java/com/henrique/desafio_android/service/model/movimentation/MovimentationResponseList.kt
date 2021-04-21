package com.henrique.desafio_android.service.model.movimentation

import com.google.gson.annotations.SerializedName

data class MovimentationResponseList(
    @SerializedName("items") val items: List<MovimentationResponse>
)
