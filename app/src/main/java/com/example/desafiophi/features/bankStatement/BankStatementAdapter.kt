package com.example.desafiophi.features.bankStatement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.data.models.responses.Statement
import com.example.desafiophi.databinding.ItemStatementBinding
import com.example.desafiophi.utils.maskBrazilianCurrency
import com.example.desafiophi.utils.toBrazilianDateFormat

class BankStatementAdapter(private val statementList: List<Statement.Item>) :
    RecyclerView.Adapter<BankStatementAdapter.PaymentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
        val itemBinding =
            ItemStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
        holder.bind(statementList[position])
    }

    override fun getItemCount(): Int = statementList.size

    class PaymentHolder(private val itemBinding: ItemStatementBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(statementItem: Statement.Item) {
            itemBinding.textTransferType.text = statementItem.tType
            itemBinding.textTransferTo.text = statementItem.to
            itemBinding.textValue.text = statementItem.amount.toDouble().maskBrazilianCurrency(true)
            itemBinding.textDate.text =
                statementItem.createdAt.toBrazilianDateFormat("yyyy-MM-dd'T'HH:mm:ss", "dd/MM")
        }
    }
}