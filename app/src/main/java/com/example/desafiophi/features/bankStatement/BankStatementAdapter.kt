package com.example.desafiophi.features.bankStatement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.R
import com.example.desafiophi.data.models.responses.Statement
import com.example.desafiophi.databinding.ItemStatementBinding
import com.example.desafiophi.utils.maskBrazilianCurrency
import com.example.desafiophi.utils.toBrazilianDateFormat

class BankStatementAdapter(
    private val statementList: MutableList<Statement.Item>,
    private val listener: (statementItem: Statement.Item) -> Unit
) :
    RecyclerView.Adapter<BankStatementAdapter.PaymentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
        val itemBinding =
            ItemStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
        holder.bind(statementList[position])
    }

    override fun getItemCount(): Int = statementList.size

    fun appendToList(list: List<Statement.Item>) {
        statementList.addAll(list)
        notifyDataSetChanged()
    }

    class PaymentHolder(
        private val itemBinding: ItemStatementBinding,
        private val listener: (statementItem: Statement.Item) -> Unit
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(statementItem: Statement.Item) {
            itemBinding.textTransferType.text = statementItem.description
            itemBinding.textTransferTo.text = statementItem.to
            itemBinding.textValue.text = statementItem.amount.toDouble().maskBrazilianCurrency(true)
            itemBinding.textDate.text =
                statementItem.createdAt.toBrazilianDateFormat("yyyy-MM-dd'T'HH:mm:ss", "dd/MM")

            configurePixCell(statementItem)

            itemBinding.mainContainer.setOnClickListener {
                listener.invoke(statementItem)
            }
        }

        private fun configurePixCell(statementItem: Statement.Item) {
            if (statementItem.tType.toLowerCase().contains("pix")) {
                itemBinding.root.setBackgroundColor(itemBinding.root.context.getColor(R.color.light_gray))
                itemBinding.textIsPix.visibility = View.VISIBLE
            } else {
                itemBinding.root.setBackgroundColor(itemBinding.root.context.getColor(R.color.white))
                itemBinding.textIsPix.visibility = View.GONE
            }
        }
    }
}
