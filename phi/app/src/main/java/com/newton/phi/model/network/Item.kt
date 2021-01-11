package com.newton.phi.model.network

import com.newton.phi.common.Payload
import com.newton.phi.common.currencyDate
import com.newton.phi.common.currencyFormat
import com.newton.phi.common.extract
import com.newton.phi.model.view.Transaction
import java.math.BigDecimal

data class Item(
    val amount: Int, // 1807
    val bankName: String, // Banco Phi
    val createdAt: String, // 2020-10-22T03:00:00Z
    val description: String, // Transferência realizada
    val from: String?, // Arthur Hunt
    val id: String, // E79E0C9A-46AE-4DBA-82F5-D0BACC53F6CF
    val tType: String, // TRANSFEROUT
    val to: String? // David Bond
): Payload<Transaction>{
    override fun toModel() = Transaction (
            id = extract safe id,
            name = when(tType) {
                "PIXCASHIN" -> from ?: ""
                "BANKSLIPCASHIN" -> from ?: ""
                "TRANSFERIN" -> from ?: ""
                else -> to ?: ""
            },
            value = "R$ ${currencyFormat(amount)}",
            description = extract safe description,
            date = currencyDate(createdAt),
            pix = when(tType) {
                "PIXCASHOUT" -> true
                "PIXCASHIN" -> true
                else -> false
            },
            credit = when(tType) {
                "PIXCASHIN" -> true
                "BANKSLIPCASHIN" -> true
                "TRANSFERIN" -> true
                else -> false
            }
    )

}