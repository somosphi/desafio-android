package com.chavesdev.phiapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chavesdev.phiapp.databinding.StatementItemBinding
import com.chavesdev.phiapp.repo.api.messages.StatementMessage

class StatementsPageAdapter(
    diffCallback: DiffUtil.ItemCallback<StatementMessage>,
    val listener: StatementListener
) :
    PagingDataAdapter<StatementMessage, StatementsPageAdapter.StatementViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        val item = getItem(position) as StatementMessage
        val viewModel = StatementItemViewModel.translateMessage(item)
        viewModel.onClick = View.OnClickListener {
            listener.onStatementClick(item.id)
        }
        holder.bind(viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StatementItemBinding.inflate(inflater, parent, false)
        return StatementViewHolder(binding)
    }

    class StatementViewHolder(private val binding: StatementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: StatementItemViewModel) {
            binding.statement = viewModel
            binding.executePendingBindings()
        }
    }

    object StatementComparator : DiffUtil.ItemCallback<StatementMessage>() {
        override fun areItemsTheSame(
            oldItem: StatementMessage,
            newItem: StatementMessage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StatementMessage,
            newItem: StatementMessage
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface StatementListener {
        fun onStatementClick(id: String)
    }
}