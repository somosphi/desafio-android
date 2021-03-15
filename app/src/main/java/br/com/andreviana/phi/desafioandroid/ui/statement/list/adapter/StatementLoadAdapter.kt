package br.com.andreviana.phi.desafioandroid.ui.statement.list.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class StatementLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<StatementLoadViewHolder>() {

    override fun onBindViewHolder(holder: StatementLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): StatementLoadViewHolder {
        return StatementLoadViewHolder.create(parent, retry)
    }
}