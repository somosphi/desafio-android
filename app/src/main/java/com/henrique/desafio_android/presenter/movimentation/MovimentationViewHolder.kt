package com.henrique.desafio_android.presenter.movimentation

import android.util.Log
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.henrique.desafio_android.databinding.ItemMovimentationBinding
import com.henrique.desafio_android.network.response.MyStatementResponse
import com.henrique.desafio_android.utils.formatCurrency
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MovimentationViewHolder(
    private val binding: ViewDataBinding,
    private val listener: MovimentationListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(movimentation: MyStatementResponse) {
        (binding as? ItemMovimentationBinding)?.run {
            movimentation.let {
                movimentationItem = MovimentationItem(
                    it.description,
                    it.to ?: "Sua Conta",
                    it.amount.formatCurrency(),
                    getDateFromUTCToStringFormatted(it.createdAt.substring(0, 10), "yyyy-MM-dd", "dd/MM"),
                    isPixTransaction(it.tType)
                )
            }

            root.setOnClickListener { listener.onListClick(movimentation) }
        }
    }

    private fun getDateFromUTCToStringFormatted(s: String, formatFrom: String, formatTo: String): String {
        try {
            val dateFormat = SimpleDateFormat(formatFrom, Locale("pt", "BR"))
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = dateFormat.parse(s)
            return getDateToStringFormatted(date, formatTo, TimeZone.getDefault())
        } catch (e: ParseException) {
            Log.d("", e.localizedMessage)
        }

        return ""
    }

    private fun getDateToStringFormatted(date: Date, formatTo: String, timeZone: TimeZone): String {
        val simpleDateFormat = SimpleDateFormat(formatTo, Locale("pt", "BR"))
        simpleDateFormat.timeZone = timeZone
        return simpleDateFormat.format(date)
    }

    private fun isPixTransaction(type: String): Boolean {
        return when (type) {
            "PIXCASHOUT", "PIXCASHIN" -> true
            else -> false
        }
    }

}