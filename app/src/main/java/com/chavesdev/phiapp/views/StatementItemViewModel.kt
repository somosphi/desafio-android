package com.chavesdev.phiapp.views

import android.view.View
import androidx.lifecycle.ViewModel
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.repo.api.messages.StatementMessage.StatementType
import com.chavesdev.phiapp.util.formatNumber

class StatementItemViewModel(
    val origin: String,
    val description: String,
    val amount: String,
    val pix: Boolean,
    val type: StatementType,
    val date: String
) :
    ViewModel() {

    lateinit var onClick : View.OnClickListener

    companion object {
        fun translateMessage(message: StatementMessage): StatementItemViewModel {
            return StatementItemViewModel(
                getOrigin(message),
                message.description,
                checkamount(message.amount, message.type).formatNumber(),
                message.type == StatementType.PIXCASHIN || message.type == StatementType.PIXCASHOUT,
                message.type,
                android.text.format.DateFormat.format("dd/MM", message.date).toString()
            )
        }

        private fun checkamount(amount: Int, type: StatementType) : Long {
            return if(type == StatementType.TRANSFEROUT || type == StatementType.PIXCASHOUT) {
                amount * -1
            } else {
                amount
            }.toLong()
        }

        private fun getOrigin(message: StatementMessage) : String {
            if(message.to != null) {
                return message.to!!
            } else if(message.from != null) {
                return message.from !!
            }
            return ""
        }
    }
}