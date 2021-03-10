package br.com.andreviana.phi.desafioandroid.ui.statement.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.common.ItemModel
import br.com.andreviana.phi.desafioandroid.databinding.AdapterItemBinding

class StatementDetailAdapter(private val listItemModel: List<ItemModel>) :
    RecyclerView.Adapter<StatementDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(listItemModel[position])


    override fun getItemCount(): Int = listItemModel.size

    class ViewHolder(private val binding: AdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(itemModel: ItemModel) {
            binding.textViewIdentifier.text = itemModel.identifier
            binding.textViewValue.text = itemModel.value
        }
    }
}