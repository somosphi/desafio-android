package br.com.phi.challenge.api.statement.data.remote

import br.com.phi.challenge.api.executeApi
import br.com.phi.challenge.api.statement.domain.StatementRepository

/**
 * Created by pcamilo on 10/01/2021.
 */
class StatementRepositoryImp(private val statementService: StatementService): StatementRepository {

    override suspend fun getStatements(limit: Int, offset: Int) = executeApi { statementService.getStatements(limit, offset) }

    override suspend fun getStatementDetail(statementId: String) = executeApi { statementService.getStatementDetail(statementId) }
}