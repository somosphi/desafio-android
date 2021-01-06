package com.example.desafiophi.utils

import br.com.concrete.canarinho.formatador.FormatadorValor

fun Double.maskBrazilianCurrency(withSymbol: Boolean = false): String = try {
    if (withSymbol)
        FormatadorValor.VALOR_COM_SIMBOLO.formata(this.toString())
    else
        FormatadorValor.VALOR.formata(this.toString())
} catch (e: IllegalArgumentException) {
    e.printStackTrace()
    ""
}