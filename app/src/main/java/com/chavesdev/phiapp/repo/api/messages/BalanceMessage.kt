package com.chavesdev.phiapp.repo.api.messages

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BalanceMessage : Serializable {

    @SerializedName("amount")
    var amount = 0L
}