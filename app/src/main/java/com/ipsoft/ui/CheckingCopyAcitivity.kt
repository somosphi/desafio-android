package com.ipsoft.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.ipsoft.ph.R
import com.ipsoft.ph.databinding.ActivityCheckingCopyAcitivityBinding

class CheckingCopyAcitivity : AppCompatActivity() {
    private lateinit var checkingBinding : ActivityCheckingCopyAcitivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingBinding = ActivityCheckingCopyAcitivityBinding.inflate(layoutInflater)
        val view = checkingBinding.root
        setContentView(view)

//        var toolbar = findViewById<Toolbar>(R.id.toolbar)
//
//        setSupportActionBar(toolbar)


    }
}