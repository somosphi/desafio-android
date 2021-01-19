package com.ipsoft.ph.repository.model

import com.google.gson.annotations.SerializedName

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

data class Balance(
    @SerializedName("amount")
    val value: Double = 0.0)
