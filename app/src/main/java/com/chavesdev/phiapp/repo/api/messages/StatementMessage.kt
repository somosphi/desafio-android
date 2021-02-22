package com.chavesdev.phiapp.repo.api.messages

import com.chavesdev.phiapp.util.formatNumber
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class StatementMessage : Serializable {

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("createdAt")
    lateinit var date: Date

    @SerializedName("amount")
    var amount = 0

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("tType")
    lateinit var type: StatementType

    @SerializedName("to")
    var to: String? = null

    @SerializedName("from")
    var from: String? = null

    @SerializedName("authentication")
    var authentication: String? = null

    @SerializedName("bankName")
    var bankName: String? = null

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

    private fun checkamount(amount: Int, type: StatementType): Long {
        return if (isMoneyOut()) {
            amount.times(-1)
        } else {
            amount
        }.toLong()
    }

    private fun isMoneyOut(): Boolean {
        return type == StatementType.TRANSFEROUT || type == StatementType.PIXCASHOUT
    }

    fun formatedNumber(): String {
        return checkamount(amount, type).formatNumber()
    }

    fun getOrigin() : String {
        if(to != null) {
            return to!!
        } else if(from != null) {
            return from !!
        }
        return ""
    }
}