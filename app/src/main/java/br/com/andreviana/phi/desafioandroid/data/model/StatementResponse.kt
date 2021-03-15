package br.com.andreviana.phi.desafioandroid.data.model

data class StatementResponse(
    val items: List<ItemResponse>
)

fun StatementResponse.mapperToItemsList() = items.map { it.mapperToStatementList() }