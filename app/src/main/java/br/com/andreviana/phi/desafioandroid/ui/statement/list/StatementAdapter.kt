package br.com.andreviana.phi.desafioandroid.ui.statement.list

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.andreviana.phi.desafioandroid.BR
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.model.StatementViewList
import br.com.andreviana.phi.desafioandroid.data.model.TransactionType
import br.com.andreviana.phi.desafioandroid.databinding.AdapterMovesBinding
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat

class StatementAdapter :
    ListAdapter<StatementViewList, StatementAdapter.ViewHolder>(STATEMENT_COMPARATOR) {

    private var _listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_moves,
                parent,
                false
            ), this
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statement = getItem(position)
        if (statement != null) holder.bindView(statement)
    }


    fun runOnItemClickListener(itemClick: OnItemClickListener) {
        _listener = itemClick
    }

    class ViewHolder(
        private val binding: AdapterMovesBinding,
        private val adapter: StatementAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: StatementViewList) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            checkTypeTransaction(item)
            binding.textViewValue.text = convertCentsToReal(item.amount).moneyFormat()
            binding.root.setOnClickListener {
                adapter._listener?.itemClick(item.id)
            }
        }

        private fun checkTypeTransaction(item: StatementViewList) {
            if (item.transactionType == TransactionType.PIXCASHIN.name
                || item.transactionType == TransactionType.PIXCASHOUT.name
            ) {
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
                            R.color.grey_custom_900
                        )
                    )
                }

                binding.imageViewPix.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private val STATEMENT_COMPARATOR = object : DiffUtil.ItemCallback<StatementViewList>() {
            override fun areItemsTheSame(
                oldItem: StatementViewList,
                newItem: StatementViewList
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: StatementViewList,
                newItem: StatementViewList
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun interface OnItemClickListener {
    fun itemClick(statementId: String)
}