package joao.moreno.phi_test.view.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import joao.moreno.phi_test.R
import joao.moreno.phi_test.model.statement.ItemStatementVO
import joao.moreno.phi_test.model.statement.StatementType
import joao.moreno.phi_test.utils.ViewUtils

class StatementAdapter(
    private var itensStatementVO: List<ItemStatementVO>,
    private val context: Context,
    val itemClickListener: (ItemStatementVO) -> Unit
) : Adapter<StatementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.statement_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = itensStatementVO.get(position)
        holder.labelOperationType.text = "${item.description}"
        if (item.to != null) {
            holder.labelRecived.text = "${item.to}"
        } else {
            holder.labelRecived.text = "Sua conta"
        }
        when (item.tType) {
            StatementType.TRANSFEROUT, StatementType.PIXCASHOUT, StatementType.BANKSLIPCASHOUT -> holder.labelOperationValue.text =
                "${ViewUtils.formatNegativeValueCurrency(item.amount)}"
            StatementType.TRANSFERIN, StatementType.PIXCASHIN, StatementType.BANKSLIPCASHIN -> holder.labelOperationValue.text =
                "${ViewUtils.formatValueCurrency(item.amount)}"

        }
        if (item.tType == StatementType.PIXCASHOUT || item.tType == StatementType.PIXCASHIN) {
            holder.backgroundOperationPix.visibility = VISIBLE
            ViewUtils.sendViewToBack(holder.backgroundOperationPix)
            holder.cardPix.visibility = View.VISIBLE
        }

        holder.labelOperationDate.text = "${DateFormat.format("dd/MM", item.createdAt)}"

        holder.clMain.setOnClickListener{
            if (hasOutput(item)) {
                itemClickListener(item)
            }
        }
    }

    fun hasOutput(item : ItemStatementVO) : Boolean {
        when(item.tType){
            StatementType.TRANSFEROUT, StatementType.PIXCASHOUT, StatementType.BANKSLIPCASHOUT -> return true
            else -> return false
        }
    }

    fun addItem(items : List<ItemStatementVO>){
        itensStatementVO += items
    }

    override fun getItemCount(): Int = itensStatementVO.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var labelOperationType: TextView = itemView.findViewById(R.id.label_operation_type)
        var labelRecived: TextView = itemView.findViewById(R.id.label_recived)
        var labelOperationDate: TextView = itemView.findViewById(R.id.label_operation_date)
        var labelOperationValue: TextView = itemView.findViewById(R.id.label_operation_value)
        var backgroundOperationPix: View = itemView.findViewById(R.id.background_operation_pix)
        var cardPix: LinearLayout = itemView.findViewById(R.id.card_pix)
        var clMain: ConstraintLayout = itemView.findViewById(R.id.cl_main)



    }
}
