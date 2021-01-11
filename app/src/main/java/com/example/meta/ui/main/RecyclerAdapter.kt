package com.example.meta.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meta.R
import com.example.meta.data.model.ListItemsResponse
import com.example.meta.extensions.formatCurrency
import com.example.meta.extensions.formatDate
import kotlinx.android.synthetic.main.item_list.view.*

class RecyclerAdapter(
    private val list: ArrayList<ListItemsResponse>,
    private val ctx: Context,
    private val onItemClickListener: ((id: String) -> Unit)
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder> () {

    companion object {
        const val VIEW_TYPE_PIX = 2

        const val PIX_IN = "PIXCASHIN"
        const val PIX_OUT = "PIXCASHOUT"

        const val YOUR_ACCOUNT = "Sua conta"
    }

    class ViewHolder(
        val view: View,
        private val onItemClickListener: ((id: String) -> Unit)
    ): RecyclerView.ViewHolder(view) {

        private val description = view.textViewDescription
        private val name = view.textViewName
        private val date = view.textViewDate
        private val amount = view.textViewValues

        fun bindView(listItem: ListItemsResponse) {
            view.setOnClickListener {
                onItemClickListener.invoke(listItem.id)
            }

            description.text = listItem.description
            amount.text = formatCurrency(listItem.amount, listItem.type)
            date.text = formatDate(listItem.createdAt)

            listItem.to?.let { to ->
                name.text = to
            } ?: run {
                name.text = YOUR_ACCOUNT
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_PIX) {
            ViewHolder(
                LayoutInflater.from(ctx).inflate(R.layout.item_list_pix, parent, false),
                onItemClickListener
            )
        } else {
            ViewHolder(
                LayoutInflater.from(ctx).inflate(R.layout.item_list, parent, false),
                onItemClickListener
            )
        }
    }

    override fun getItemCount() = list.count()

    override fun getItemViewType(position: Int): Int {
        if (list[position].type == PIX_IN || list[position].type == (PIX_OUT)) {
            return VIEW_TYPE_PIX
        }

        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list[position])
    }
}