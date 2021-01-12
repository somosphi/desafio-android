package br.com.phi.challenge.api.statement.domain.model

import androidx.databinding.BaseObservable

/**
 * Created by pcamilo on 10/01/2021.
 */
class StatementModel(val id: String, val description: String, val amount: String, val destiny: String? = "", val date: String? = "", val statementType: String, val authentication: String? = ""): BaseObservable()