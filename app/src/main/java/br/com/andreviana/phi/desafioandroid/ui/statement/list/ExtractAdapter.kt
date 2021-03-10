package br.com.andreviana.phi.desafioandroid.ui.statement.list

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.andreviana.phi.desafioandroid.BR
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.model.Item
import br.com.andreviana.phi.desafioandroid.data.model.TransactionType
import br.com.andreviana.phi.desafioandroid.databinding.AdapterMovesBinding
import br.com.andreviana.phi.desafioandroid.util.ktx.convertCentsToReal
import br.com.andreviana.phi.desafioandroid.util.ktx.moneyFormat

class ExtractAdapter(
    private val itemList: List<Item>,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<ExtractAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_moves,
                parent,
                false
            ), this
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(itemList[position])

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(
        private val binding: AdapterMovesBinding,
        private val adapter: ExtractAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Item) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            checkTypeTransaction(item)
            binding.textViewValue.text = convertCentsToReal(item.amount).moneyFormat()
            binding.root.setOnClickListener {
                adapter.itemClickListener.invoke(item.id)
            }
        }

        private fun checkTypeTransaction(item: Item) {
            if (item.tType == TransactionType.PIXCASHIN.name
                || item.tType == TransactionType.PIXCASHOUT.name
            ) {
                val mode =
                    binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

                if (mode == Configuration.UI_MODE_NIGHT_NO){
                    binding.cardViewMoves.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.grey_custom_100
                        )
                    )
                }else{
                    binding.cardViewMoves.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.grey_custom_900
                        )
                    )
                }

                binding.imageViewPix.visibility = View.VISIBLE
            }
        }
    }
}