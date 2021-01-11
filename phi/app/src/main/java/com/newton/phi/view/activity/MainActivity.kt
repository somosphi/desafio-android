package com.newton.phi.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.newton.phi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragment)// where nav_host_fragment is the id for your Main NavHost fragment
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.extractFragment,
                R.id.receiptFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}