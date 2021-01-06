package com.example.desafiophi.features.bankStatement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiophi.data.models.responses.StatementItem
import com.example.desafiophi.databinding.ItemStatementBinding
import com.example.desafiophi.utils.maskBrazilianCurrency

class BankStatementAdapter(private val statementList: List<StatementItem>) :
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
        fun bind(statementItem: StatementItem) {
            itemBinding.textTransferType.text = statementItem.tType
            itemBinding.textTransferTo.text = statementItem.to
            itemBinding.textValue.text = statementItem.amount.toDouble().maskBrazilianCurrency(true)
            itemBinding.textValue.text = statementItem.createdAt
        }
    }
}