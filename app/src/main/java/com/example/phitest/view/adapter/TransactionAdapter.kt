package com.example.phitest.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.phitest.R
import com.example.phitest.service.model.Transaction
import com.example.phitest.service.model.TransactionTypeEnum
import com.example.phitest.util.VsFunctions
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

class TransactionAdapter(private val mTransactions: List<Transaction.Items>, private val context: Context) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtType = itemView.findViewById<TextView>(R.id.txtType)
        val txtTo = itemView.findViewById<TextView>(R.id.txtTo)
        val txtAmount = itemView.findViewById<TextView>(R.id.txtAmount)
        val txtDate = itemView.findViewById<TextView>(R.id.txtDate)
        val txtPix = itemView.findViewById<TextView>(R.id.txtPix)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val transactionView = inflater.inflate(R.layout.row_transaction, parent, false)

        return ViewHolder(transactionView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction: Transaction.Items = mTransactions.get(position)

        val txtType = holder.txtType
        val txtTo = holder.txtTo
        val txtDate = holder.txtDate
        val txtAmount = holder.txtAmount
        val txtPix = holder.txtPix

        var dateStr = ""
        try {
            val date = ZonedDateTime.parse(transaction.createdAt)
            dateStr = dateStr.plus(date.dayOfMonth.toString().plus("/").plus(date.monthValue))
        } catch (e: DateTimeParseException) {
            //TODO
        }

        when(transaction.tType){
            TransactionTypeEnum.TRANSFEROUT.name -> {
                txtType.text = TransactionTypeEnum.TRANSFEROUT.value
                txtPix.visibility = View.INVISIBLE
            }
            TransactionTypeEnum.TRANSFERIN.name -> {
                txtType.text = TransactionTypeEnum.TRANSFERIN.value
                txtPix.visibility = View.INVISIBLE
            }
            TransactionTypeEnum.BANKSLIPCASHIN.name -> {
                txtType.text = TransactionTypeEnum.BANKSLIPCASHIN.value
                txtPix.visibility = View.INVISIBLE
            }
            TransactionTypeEnum.PIXCASHIN.name -> {
                txtType.text = TransactionTypeEnum.PIXCASHIN.value
                txtPix.visibility = View.VISIBLE
            }
            TransactionTypeEnum.PIXCASHOUT.name -> {
                txtType.text = TransactionTypeEnum.PIXCASHOUT.value
                txtPix.visibility = View.VISIBLE
            }
        }

        txtTo.text = transaction.to
        txtDate.text = dateStr
        txtAmount.text = VsFunctions.formatToCurrency(transaction.amount)
    }

    override fun getItemCount(): Int {
        return mTransactions.size
    }

}