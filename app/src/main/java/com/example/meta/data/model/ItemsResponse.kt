package com.example.meta.data.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse (
    @SerializedName("items")
    var items : ArrayList<ListItemsResponse>
)

data class ListItemsResponse (
        @SerializedName("createdAt")
        var createdAt : String,
        @SerializedName("id")
        var id : String,
        @SerializedName("amount")
        var amount : Int,
        @SerializedName("from")
        var from : String,
        @SerializedName("to")
        val to : String?,
        @SerializedName("description")
        var description : String,
        @SerializedName("tType")
        var type : String,
        @SerializedName("bankName")
        var bankName : String?
)