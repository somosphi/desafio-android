package com.example.desafiophi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiophi.architecture.android.viewBinding
import com.example.desafiophi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
