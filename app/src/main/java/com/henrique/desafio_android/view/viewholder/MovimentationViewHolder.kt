package com.henrique.desafio_android.view.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.henrique.desafio_android.databinding.ItemMovimentationBinding
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.domain.model.movimentation.MovimentationItem
import com.henrique.desafio_android.domain.listener.MovimentationListener
import com.henrique.desafio_android.domain.utils.formatCurrency
import com.henrique.desafio_android.domain.utils.isPixTransaction
import com.henrique.desafio_android.domain.utils.getDateFormatted
import com.henrique.desafio_android.domain.utils.getPerson

class MovimentationViewHolder(
    private val binding: ViewDataBinding,
    private val listener: MovimentationListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(movimentation: MovimentationResponse) {
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