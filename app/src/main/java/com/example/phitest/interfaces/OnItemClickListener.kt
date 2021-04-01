package com.example.phitest.interfaces

import com.example.phitest.model.Transaction

interface OnItemClickListener {
    fun onItemClicked(item: Transaction.Items?)
}