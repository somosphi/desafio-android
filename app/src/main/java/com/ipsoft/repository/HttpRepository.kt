package com.ipsoft.repository

import com.ipsoft.repository.model.Transaction

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       19/01/2021
 */

interface HttpRepository{
    fun getBalance() : Double
    fun getStatement() : List<Transaction>

    companion object {
        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}
