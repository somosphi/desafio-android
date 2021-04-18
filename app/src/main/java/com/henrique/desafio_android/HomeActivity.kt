package com.henrique.desafio_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.henrique.desafio_android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val mViewModel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).apply {
            lifecycleOwner = this@HomeActivity
            setVariable(BR.viewModel, mViewModel)
        }
    }

}