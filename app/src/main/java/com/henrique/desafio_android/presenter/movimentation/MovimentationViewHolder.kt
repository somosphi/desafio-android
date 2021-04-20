package com.henrique.desafio_android.presenter.movimentation

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.henrique.desafio_android.databinding.ItemMovimentationBinding
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.utils.formatCurrency
import com.henrique.desafio_android.utils.isPixTransaction
import com.henrique.desafio_android.utils.getDateFormatted
import com.henrique.desafio_android.utils.getPerson

class MovimentationViewHolder(
    private val binding: ViewDataBinding,
    private val listener: MovimentationListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(movimentation: MyStatementResponse) {
        (binding as? ItemMovimentationBinding)?.run {
            movimentation.let {
                movimentationItem = MovimentationItem(
                    it.description,
                    getPerson(it.to.orEmpty(), it.from.orEmpty()),
                    it.amount.formatCurrency(),
                    it.createdAt.substring(0, 10).getDateFormatted(),
                    it.tType.isPixTransaction()
                )
            }
            root.setOnClickListener { listener.onListClick(movimentation.id) }
        }
    }

}