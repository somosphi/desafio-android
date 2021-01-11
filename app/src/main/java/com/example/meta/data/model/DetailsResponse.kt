package com.example.meta.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailsResponse (
    @SerializedName("amount")
    var amount : Int,
    @SerializedName("id")
    var id : String,
    @SerializedName("authentication")
    var authentication : String,
    @SerializedName("tType")
    var type : String,
    @SerializedName("createdAt")
    var createdAt : String,
    @SerializedName("to")
    var to : String?,
    @SerializedName("description")
    var description : String,
    @SerializedName("bankName")
    var bankName : String?
) : Serializable