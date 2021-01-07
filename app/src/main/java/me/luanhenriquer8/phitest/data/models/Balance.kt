package me.luanhenriquer8.phitest.data.models

import me.luanhenriquer8.phitest.utils.extractValueAsCurrency

data class Balance(
    var amount: Long
) {
    fun extractAmountAsCurrency(): String {
        return extractValueAsCurrency(amount)
    }
}
