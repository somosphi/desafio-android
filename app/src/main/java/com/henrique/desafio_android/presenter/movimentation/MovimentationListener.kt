package com.henrique.desafio_android.presenter.movimentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class MovimentationListener(
    private val layoutManager: LinearLayoutManager,
    private val askForMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    abstract fun onListClick(id: String)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        if (lastVisiblePosition == (totalItemCount - 1)) {
            askForMore()
        }
    }
}