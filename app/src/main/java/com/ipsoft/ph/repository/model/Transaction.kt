package com.ipsoft.ph.repository.model

import com.google.gson.annotations.SerializedName

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       18/01/2021
 */


data class Transaction(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("authentication")
    val authentication: String,
    @SerializedName("tType")
    val tType: String,
    @SerializedName("createdAt")
    val createdAd: String,
    @SerializedName("to")
    val receiver: String,
    @SerializedName("description")
    val description: String
)