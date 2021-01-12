package br.com.phi.challenge.api.statement.domain

import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.statement.data.model.StatementItemResponse
import br.com.phi.challenge.api.statement.data.model.StatementResponse

/**
 * Created by pcamilo on 10/01/2021.
 */
interface StatementRepository {
    suspend fun getStatements(limit: Int = 10, offset: Int): Result<StatementResponse>
    suspend fun getStatementDetail(statementId: String): Result<StatementItemResponse>
}