package com.newton.phi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newton.phi.R
import com.newton.phi.databinding.ViewholderItemDetailBinding
import com.newton.phi.model.view.ItemDetail

class DetailAdapter () : RecyclerView.Adapter<DetailAdapter.ItemDetailAdapterViewHolder>() {

    private var list: List<ItemDetail>? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ItemDetailAdapterViewHolder {
        val binding: ViewholderItemDetailBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.viewholder_item_detail,
                parent,
                false
        )
        return ItemDetailAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemDetailAdapterViewHolder, position: Int) {

        holder.binding?.run {
            list?.get(position).let { item ->
                detail = item
            }

        }
    }

    override fun getItemCount(): Int {
        list?.run {
            return this.size
        }
        return 0
    }

    class ItemDetailAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewholderItemDetailBinding? = DataBindingUtil.bind(view)
    }

    fun setListInAdapter(list: List<ItemDetail>) {
        this.list = list
        notifyDataSetChanged()
    }
}