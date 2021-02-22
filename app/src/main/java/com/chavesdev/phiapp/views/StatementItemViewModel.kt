package com.chavesdev.phiapp.views

import android.view.View
import androidx.lifecycle.ViewModel
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.repo.api.messages.StatementMessage.StatementType
import com.chavesdev.phiapp.util.format
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
                message.getOrigin(),
                message.description,
                message.formatedNumber(),
                message.type == StatementType.PIXCASHIN || message.type == StatementType.PIXCASHOUT,
                message.type,
                message.date.format("dd/MM")
            )
        }
    }
}