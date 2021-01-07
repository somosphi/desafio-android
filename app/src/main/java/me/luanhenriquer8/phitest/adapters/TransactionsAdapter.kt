package me.luanhenriquer8.phitest.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import io.supercharge.shimmerlayout.ShimmerLayout
import me.luanhenriquer8.phitest.R
import me.luanhenriquer8.phitest.data.models.Statement
import me.luanhenriquer8.phitest.ui.details.TransactionDetailActivity
import me.luanhenriquer8.phitest.utils.STATEMENT_ID

class TransactionsAdapter(
    private val context: Context,
    private val list: ArrayList<Statement>
) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    if (list.isNotEmpty()) R.layout.item_transaction else R.layout.item_transaction_loading,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        if (list.isNotEmpty()) {
            val statement = list[position]
            holder.amount.text = statement.extractAmountAsCurrency()
            holder.type.text = statement.description
            holder.createdAt.text = statement.extractCreatedAtFormatted()
            holder.to.text = getToTransaction(statement.to)
            holder.pixBlock.visibility = mustShowPixBlock(statement.description)
            holder.parentLayout.setOnClickListener { redirectToTransaction(statement.id) }

            if (position == list.size.minus(1)) {
                val intent = Intent()
                intent.action = context.getString(R.string.increment_statement_list)
                context.sendBroadcast(intent)
            }

        } else holder.loading.startShimmerAnimation()
    }

    private fun getToTransaction(to: String): CharSequence? {
        return if (to != null && to.isNotEmpty()) to else context.getText(R.string.unknown)
    }

    private fun mustShowPixBlock(description: String): Int {
        return if (description.contains(PIX_TRANSACTION, false)) View.VISIBLE else View.INVISIBLE
    }

    private fun redirectToTransaction(statementId: String) {
        val intent = Intent(context, TransactionDetailActivity::class.java).apply {
            putExtra(STATEMENT_ID, statementId)
        }

        context.startActivity(intent)
    }

    override fun getItemCount() = if (list.isEmpty()) 8 else list.size

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val loading = view.findViewById<ShimmerLayout>(R.id.transactionLoading)
        internal val parentLayout =
            view.findViewById<ConstraintLayout>(R.id.transactionParentLayout)
        internal val pixBlock = view.findViewById<LinearLayout>(R.id.pixBlock)
        internal val to = view.findViewById<MaterialTextView>(R.id.to)
        internal val type = view.findViewById<MaterialTextView>(R.id.type)
        internal val amount = view.findViewById<MaterialTextView>(R.id.amount)
        internal val createdAt = view.findViewById<MaterialTextView>(R.id.createdAt)
    }

    companion object {
        const val PIX_TRANSACTION = "Transferência PIX"
    }

    fun addItems(list: List<Statement>) {
        list.forEach { add(it) }
    }

    private fun add(statement: Statement) {
        this.list.add(statement)
        notifyItemInserted(if (list.isEmpty()) 0 else list.size - 1)
    }
}

