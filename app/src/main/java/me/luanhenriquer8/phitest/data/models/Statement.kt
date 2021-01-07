package me.luanhenriquer8.phitest.data.models

import me.luanhenriquer8.phitest.utils.extractValueAsCurrency
import org.joda.time.DateTime

data class Statement(
    var id: String,
    var createdAt: String,
    var amount: Long,
    var to: String,
    var description: String,
    var tType: String,
    var authentication: String,
    var bankName: String
) {

    fun extractAmountAsCurrency(): String {
        return extractValueAsCurrency(amount)
    }

    fun extractCreatedAtFormatted(): CharSequence {
        val parsedDate = DateTime.parse(createdAt)
        return "${parsedDate.dayOfMonth().get()}/${parsedDate.monthOfYear().get()}"
    }
}
