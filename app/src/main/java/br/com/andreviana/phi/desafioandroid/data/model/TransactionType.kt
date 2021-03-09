package br.com.andreviana.phi.desafioandroid.data.model

enum class TransactionType(val value: String) {
    PIXCASHIN("Transferência de pix recebida."),
    PIXCASHOUT("Transferência de pix realizada")
}