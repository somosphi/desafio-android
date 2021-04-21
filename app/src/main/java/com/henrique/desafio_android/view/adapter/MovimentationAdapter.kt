package com.henrique.desafio_android.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.henrique.desafio_android.R
import com.henrique.desafio_android.service.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.service.listener.MovimentationListener
import com.henrique.desafio_android.view.viewholder.MovimentationViewHolder

class MovimentationAdapter(context: Context) : RecyclerView.Adapter<MovimentationViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val mList: MutableList<MovimentationResponse> = arrayListOf()
    private lateinit var mListener: MovimentationListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return MovimentationViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: MovimentationViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: MovimentationListener) {
        mListener = listener
    }

    fun updateList(list: MutableList<MovimentationResponse>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movimentation
    }

}