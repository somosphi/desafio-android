package com.ipsoft.ph.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipsoft.ph.databinding.ActivityStatementBinding

class StatementActivity : AppCompatActivity() {
    private lateinit var statementBiding: ActivityStatementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statementBiding = ActivityStatementBinding.inflate(layoutInflater)
        val view = statementBiding.root
        setContentView(view)


    }
}