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
    val amount: Double = 0.0,
    @SerializedName("id")
    val id: String = "0",
    @SerializedName("authentication")
    val authentication: String = "" ,
    @SerializedName("tType")
    val tType: String = "",
    @SerializedName("createdAt")
    val createdAd: String = "",
    @SerializedName("to")
    val sender: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("error")
    val error: Boolean = false,
    @SerializedName("reason")
    val reason: String = ""
)