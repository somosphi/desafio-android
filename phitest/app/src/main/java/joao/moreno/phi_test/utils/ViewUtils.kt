package joao.moreno.phi_test.utils

import android.view.View
import android.view.ViewGroup
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


object ViewUtils {

    private var locale = Locale("pt", "BR")
    private val FORMAT_CURRENCY = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    private var symbol = FORMAT_CURRENCY.currency?.symbol

    fun formatValueCurrency(valor: Double?): String? {
        FORMAT_CURRENCY.negativePrefix = "- $symbol "
        FORMAT_CURRENCY.positivePrefix = "$symbol"
        return if (valor != null) FORMAT_CURRENCY.format(BigDecimal(valor)) else FORMAT_CURRENCY.format(
            BigDecimal(
                0
            )
        )
    }

    fun formatNegativeValueCurrency(valor: Double?): String? {
        FORMAT_CURRENCY.negativePrefix = "- $symbol "
        return if (valor != null) FORMAT_CURRENCY.format(BigDecimal(-valor)) else FORMAT_CURRENCY.format(
            BigDecimal(
                0
            )
        )
    }

    fun sendViewToBack(child: View) {
        val parent = child.parent as ViewGroup
        parent.removeView(child)
        parent.addView(child, 0)
    }

}