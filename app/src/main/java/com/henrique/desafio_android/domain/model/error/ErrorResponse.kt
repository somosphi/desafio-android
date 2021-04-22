package com.henrique.desafio_android.domain.model.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String,
    @SerializedName("path") val path: String?
)
