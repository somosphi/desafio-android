package br.com.phi.challenge.view.statement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import br.com.phi.challenge.api.statement.domain.model.StatementModel
import br.com.phi.challenge.databinding.LayoutItemStatementBinding

/**
 * Created by pcamilo on 10/01/2021.
 */
class StatementAdapter(private val statementId: MutableLiveData<String>) : RecyclerView.Adapter<StatementAdapter.StatementViewHolder>() {

    private var statements = arrayListOf<StatementModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder = StatementViewHolder(LayoutItemStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount(): Int = statements.size
    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) = holder.bind(statements[position])

    inner class StatementViewHolder(private val binding: LayoutItemStatementBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StatementModel) = with(binding) {
            model = item
            root.setOnClickListener { statementId.postValue(item.id) }
            executePendingBindings()
        }
    }

    fun addItems(data: List<StatementModel>) {
        statements.addAll(data)
        notifyDataSetChanged()
    }

}