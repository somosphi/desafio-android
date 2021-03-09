package br.com.andreviana.phi.desafioandroid.data.network

object Endpoints {
    const val BASE_URL = "https://desafio-mobile-bff.herokuapp.com"
    const val BALANCE = "/myBalance"
    const val STATEMENT = "/myStatement/{limit}/{offset}"
    const val STATEMENT_DETAIL = "/myStatement/detail/{id}/"
}