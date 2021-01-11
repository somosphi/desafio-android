package com.newton.phi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newton.phi.R
import com.newton.phi.databinding.ViewholderItemMovementBinding
import com.newton.phi.model.view.Transaction

class TransactionsAdapter(private val onClick: (String) -> Unit) : RecyclerView.Adapter<TransactionsAdapter.TransactionAdapterViewHolder>() {

    private var list: List<Transaction>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapterViewHolder {
        val binding: ViewholderItemMovementBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_item_movement,
            parent,
            false
        )
        return TransactionAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TransactionAdapterViewHolder, position: Int) {

        holder.binding?.run {
            list?.get(position).let { item ->
                var cash = item?.value ?: ""
                if (!item?.credit!!){
                    cash = "- $cash"
                }

                val itemView = Transaction(
                    description = item.description ?: "",
                    name = item.name,
                    value = cash,
                    pix = item.pix,
                    date = item.date,
                        id = item.id,
                        credit = item.credit
                )
                data = itemView
            }

            root.setOnClickListener { onClick(list!![position].id) }
        }
    }

    override fun getItemCount(): Int {
        list?.run {
            return this.size
        }
        return 0
    }

    class TransactionAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewholderItemMovementBinding? = DataBindingUtil.bind(view)
    }

    fun setListInAdapter(list: List<Transaction>) {
        this.list = list
        notifyDataSetChanged()
    }
}