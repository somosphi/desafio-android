package com.example.pedroneryphi.view.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedroneryphi.R
import com.example.pedroneryphi.base.BaseFragment
import com.example.pedroneryphi.databinding.MainFragmentBinding
import com.example.pedroneryphi.model.TransferDetail
import com.example.pedroneryphi.util.extensions.formatBalancePresentation
import com.example.pedroneryphi.view.adapters.TransfersAdapter
import com.example.pedroneryphi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainFragmentBinding>() {

    private var adapter: TransfersAdapter? = null
    private var transferList: MutableList<TransferDetail> = mutableListOf()
    private var page = 0
    private var isLoading = true
    private var isFirstLoad = true

    override val fragmentLayout: Int = R.layout.main_fragment

    private val mainViewModel: MainViewModel by viewModel()

    override fun init() {
        initAdapter()
        configureBalanceVisibility()
        initObservables()
    }

    private fun initAdapter() {
        val mLayoutManager = LinearLayoutManager(context)
        adapter = TransfersAdapter(transferList, findNavController())
        bind.recyclerTransfers.adapter = adapter
        bind.recyclerTransfers.layoutManager = mLayoutManager
        bind.recyclerTransfers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition()
                    if ((lastVisibleItem+1) == (page+1) * 8 || (lastVisibleItem == 7 && isFirstLoad)) {
                        if (!isLoading) {
                            page += 1
                            mainViewModel.findTransferList(8, page)
                            isLoading = false
                            isFirstLoad = false
                        }
                    }
                }

            }
        })
    }

    private fun initObservables() {
        mainViewModel.findTransferList(8, page)
        mainViewModel.findBalance()
        mainViewModel.transfers.observe(this, Observer {
            transferList.addAll(it)
            isLoading = false
            adapter?.notifyDataSetChanged()
        })
        mainViewModel.balance.observe(this, Observer {
            bind.textBalance.text = it.formatBalancePresentation()
        })
    }

    private fun configureBalanceVisibility() {
        bind.showBalance.setOnClickListener {
            if (placeHolderBalance.visibility == View.VISIBLE) {
                bind.showBalance.setImageResource(R.drawable.ic_invisible)
                bind.placeHolderBalance.visibility = View.INVISIBLE
                bind.textBalance.visibility = View.VISIBLE
            } else {
                bind.showBalance.setImageResource(R.drawable.ic_visible)
                bind.placeHolderBalance.visibility = View.VISIBLE
                bind.textBalance.visibility = View.INVISIBLE
            }

        }
    }

}