package br.com.andreviana.phi.desafioandroid.ui.statement.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import br.com.andreviana.phi.desafioandroid.data.common.ItemModel
import br.com.andreviana.phi.desafioandroid.data.model.StatementViewDetail
import br.com.andreviana.phi.desafioandroid.data.repository.StatementRepository
import br.com.andreviana.phi.desafioandroid.util.helper.DateFormat
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat

class StatementDetailViewModel
@ViewModelInject constructor(
    private val statementRepository: StatementRepository
) : ViewModel() {

    fun getStatementDetail(id: String) = liveData {
        emitSource(statementRepository.fetchStatementDetail(id).asLiveData())
    }

    fun createViewProof(detail: StatementViewDetail) = if (isBankNameValid(detail.bankName)) {
        listOf(
            ItemModel("Tipo de movimentação", detail.description),
            ItemModel("Valor", convertCentsToReal(detail.amount).moneyFormat()),
            ItemModel("Recebedor", detail.to),
            ItemModel("Instituição bancária", detail.bankName),
            ItemModel("Data/Hora", DateFormat.dateHourCompleteFormat(detail.createdAt)),
            ItemModel("Autenticação", detail.authentication)
        )
    } else {
        listOf(
            ItemModel("Tipo de movimentação", detail.description),
            ItemModel("Valor", convertCentsToReal(detail.amount).moneyFormat()),
            ItemModel("Recebedor", detail.to),
            ItemModel("Data/Hora", DateFormat.dateHourCompleteFormat(detail.createdAt)),
            ItemModel("Autenticação", detail.authentication)
        )
    }

    private fun isBankNameValid(name: String?) = !name.isNullOrEmpty()
}