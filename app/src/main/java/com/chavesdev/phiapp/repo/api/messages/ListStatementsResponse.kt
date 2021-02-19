package com.chavesdev.phiapp.repo.api.messages

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ListStatementsResponse : Serializable {

    @SerializedName("items")
    var items: MutableList<StatementMessage> = ArrayList()
}