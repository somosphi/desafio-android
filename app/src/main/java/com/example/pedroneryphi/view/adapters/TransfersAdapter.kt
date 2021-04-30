package com.example.pedroneryphi.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pedroneryphi.databinding.TransferListItemBinding
import com.example.pedroneryphi.model.TransferDetail
import com.example.pedroneryphi.util.extensions.formatBalancePresentation
import com.example.pedroneryphi.util.extensions.formateDate
import com.example.pedroneryphi.view.fragments.MainFragmentDirections

class TransfersAdapter(
    private val items: List<TransferDetail>,
    private val navController: NavController
) : RecyclerView.Adapter<TransfersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransferListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: TransferListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransferDetail) {
            binding.pixItemList.visibility = View.INVISIBLE
            binding.backgroundPix.visibility = View.INVISIBLE

            binding.titleItemList.text = item.description
            binding.nameItemList.text = item.to
            binding.dateItemList.text = item.createdAt.formateDate()

            val price = item.amount.toString().formatBalancePresentation()
            binding.priceItemList.text =
                if (item.tType == "TRANSFEROUT") "- $price"
                else price

            if(item.description.contains("PIX")){
                binding.pixItemList.visibility = View.VISIBLE
                binding.backgroundPix.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
            binding.itemLayout.setOnClickListener {
                navController.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(item.id))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
}
