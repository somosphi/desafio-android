package com.example.phitest.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.phitest.R
import com.example.phitest.interfaces.OnItemClickListener
import com.example.phitest.model.Transaction
import com.example.phitest.model.TransactionTypeEnum
import com.example.phitest.util.VsFunctions
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

class TransactionRecyclerViewAdapter(
        private var mTransactions: ArrayList<Transaction.Items?>,
        private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder>(){

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView)

    inner class LoadingViewHolder(itemView: View) : ViewHolder(itemView)

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(transaction: Transaction.Items?, clickListener: OnItemClickListener) {
            //bind data
            itemView.setOnClickListener {
                clickListener.onItemClicked(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VsFunctions.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_transaction, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == VsFunctions.VIEW_TYPE_ITEM) {
            val transaction: Transaction.Items? = mTransactions[position]

            val txtType = holder.itemView.findViewById<TextView>(R.id.txtType)
            val txtToFrom = holder.itemView.findViewById<TextView>(R.id.txtTo)
            val txtDate = holder.itemView.findViewById<TextView>(R.id.txtDate)
            val txtAmount = holder.itemView.findViewById<TextView>(R.id.txtAmount)
            val txtPix = holder.itemView.findViewById<TextView>(R.id.txtPix)

            var dateStr = ""
            try {
                val date = ZonedDateTime.parse(transaction?.createdAt)
                dateStr = dateStr.plus(date.dayOfMonth.toString().plus("/").plus(date.monthValue))
            } catch (e: DateTimeParseException) {
                //TODO
            }

            when (transaction?.tType) {
                TransactionTypeEnum.TRANSFEROUT.name -> { txtPix.visibility = View.INVISIBLE }
                TransactionTypeEnum.TRANSFERIN.name -> { txtPix.visibility = View.INVISIBLE }
                TransactionTypeEnum.BANKSLIPCASHIN.name -> { txtPix.visibility = View.INVISIBLE }
                TransactionTypeEnum.PIXCASHIN.name -> { txtPix.visibility = View.VISIBLE }
                TransactionTypeEnum.PIXCASHOUT.name -> { txtPix.visibility = View.VISIBLE }
            }

            transaction?.to?.let { txtToFrom.text = it }

            transaction?.from?.let { txtToFrom.text = it }

            txtType.text = transaction?.description
            txtDate.text = dateStr
            txtAmount.text = transaction?.amount?.let { VsFunctions.formatToCurrency(it) }

            holder.bind(mTransactions[position], itemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return mTransactions.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mTransactions[position] == null) {
            VsFunctions.VIEW_TYPE_LOADING
        } else {
            VsFunctions.VIEW_TYPE_ITEM
        }
    }

    fun addData(dataViews: List<Transaction.Items?>) {
        mTransactions.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        mTransactions.add(null)
        notifyItemInserted(mTransactions.size - 1)
    }

    fun removeLoadingView() {
        if (mTransactions.size != 0) {
            mTransactions.removeAt(mTransactions.size - 1)
            notifyItemRemoved(mTransactions.size)
        }
    }

}