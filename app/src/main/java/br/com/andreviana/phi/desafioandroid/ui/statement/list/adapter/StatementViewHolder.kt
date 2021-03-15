package br.com.andreviana.phi.desafioandroid.ui.statement.list.adapter

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.andreviana.phi.desafioandroid.BR
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.model.StatementViewList
import br.com.andreviana.phi.desafioandroid.data.model.TransactionType
import br.com.andreviana.phi.desafioandroid.databinding.AdapterMovesBinding
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat


class StatementViewHolder(
    private val binding: AdapterMovesBinding,
    private val listener: OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StatementViewList) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        checkTypeTransaction(item)
        binding.textViewValue.text = convertCentsToReal(item.amount).moneyFormat()
        binding.root.setOnClickListener {
            listener?.itemClick(item.id)
        }
    }

    private fun checkTypeTransaction(item: StatementViewList) {
        if (item.transactionType == TransactionType.PIXCASHIN.name
            || item.transactionType == TransactionType.PIXCASHOUT.name
        ) {
            changeBackgroundColor()
            binding.imageViewPix.visibility = View.VISIBLE
        } else {
            changeBackgroundColorDefault()
            binding.imageViewPix.visibility = View.GONE
        }
    }

    private fun changeBackgroundColor() {
        val mode =
            binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        if (mode == Configuration.UI_MODE_NIGHT_NO) {
            binding.cardViewMoves.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.grey_custom_100
                )
            )
        } else {
            binding.cardViewMoves.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
            )
        }
    }

    private fun changeBackgroundColorDefault() {
        val mode =
            binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        if (mode == Configuration.UI_MODE_NIGHT_NO) {
            binding.cardViewMoves.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        } else {
            binding.cardViewMoves.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.blackLighter
                )
            )
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: OnItemClickListener?): StatementViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_moves, parent, false)
            val binding = AdapterMovesBinding.bind(view)
            return StatementViewHolder(binding, listener)
        }
    }
}
