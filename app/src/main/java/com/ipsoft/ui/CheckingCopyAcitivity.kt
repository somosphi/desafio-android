package com.ipsoft.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.ipsoft.ph.R

class CheckingCopyAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_copy_acitivity)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)


    }
}