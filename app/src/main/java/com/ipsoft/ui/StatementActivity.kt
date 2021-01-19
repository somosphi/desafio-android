package com.ipsoft.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsoft.ph.R

class StatementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement)

        startActivity(Intent(this,CheckingCopyAcitivity::class.java))

    }
}