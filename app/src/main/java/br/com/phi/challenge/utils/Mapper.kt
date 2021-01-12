package br.com.phi.challenge.utils

import br.com.phi.challenge.api.balance.data.model.BalanceResponse
import br.com.phi.challenge.api.balance.domain.model.BalanceModel
import br.com.phi.challenge.api.statement.data.model.StatementItemResponse
import br.com.phi.challenge.api.statement.data.model.StatementResponse
import br.com.phi.challenge.api.statement.domain.model.StatementModel

/**
 * Created by pcamilo on 10/01/2021.
 */


fun BalanceResponse.toBalanceModel() = BalanceModel(amount = amount.formatToCurrencyBRL())

fun StatementResponse.toStatements() = this.items.map { statement -> statement.toStatementModel() }

fun StatementItemResponse.toStatementModel() = StatementModel(
    id = id,
    description = description,
    destiny = destiny ?: from,
    date = createdAt.formatDateBR(DATE_STATEMENT),
    amount = amount.formatToCurrencyBRL(),
    statementType = statementType
)

fun StatementItemResponse.toStatementDetailModel() = StatementModel(
        id = id,
        description = description,
        destiny = destiny ?: from,
        date = createdAt.formatDateBR(DATE_STATEMENT_DETAIL),
        amount = amount.formatToCurrencyBRL(),
        statementType = statementType,
        authentication = authentication
)
