package com.chavesdev.phiapp.repo.api.messages

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class StatementMessage : Serializable {

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("createdAt")
    lateinit var date : Date

    @SerializedName("amount")
    var amount = 0

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("tType")
    lateinit var type: StatementType

    @SerializedName("to")
    var to : String? = null

    @SerializedName("from")
    var from: String? = null

    @SerializedName("authentication")
    var authentication: String? = null

    enum class StatementType {
        TRANSFEROUT,
        TRANSFERIN,
        PIXCASHIN,
        PIXCASHOUT,
        BANKSLIPCASHIN
    }

    override fun equals(other: Any?): Boolean {
        other as StatementMessage
        return other.id == this.id && other.type == this.type
    }
}