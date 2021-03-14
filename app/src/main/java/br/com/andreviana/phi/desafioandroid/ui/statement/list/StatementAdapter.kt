package br.com.andreviana.phi.desafioandroid.ui.statement.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.andreviana.phi.desafioandroid.data.model.ItemResponse
import br.com.andreviana.phi.desafioandroid.data.model.StatementViewList

class StatementAdapter :
    PagingDataAdapter<StatementViewList, StatementViewHolder>(STATEMENT_COMPARATOR) {

    private var _listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        return StatementViewHolder.create(parent, _listener)
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        val statement = getItem(position)
        if (statement != null) holder.bind(statement)
    }

    fun runOnItemClickListener(itemClick: OnItemClickListener) {
        _listener = itemClick
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