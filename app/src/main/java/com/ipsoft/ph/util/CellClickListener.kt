package com.ipsoft.ph.util

import com.ipsoft.ph.repository.model.Transaction

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       20/01/2021
 */

interface CellClickListener {
    fun onCellClickListener(data: Transaction)
}