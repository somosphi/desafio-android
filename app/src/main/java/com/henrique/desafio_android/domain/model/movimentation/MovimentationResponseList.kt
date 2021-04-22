package com.henrique.desafio_android.domain.model.movimentation

import com.google.gson.annotations.SerializedName

data class MovimentationResponseList(
    @SerializedName("items") val items: List<MovimentationResponse>
)
