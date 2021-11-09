package com.esaudev.aristicomp.walker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.databinding.ActivityWalkerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkerActivity : AppCompatActivity() {

    private var _binding : ActivityWalkerBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWalkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fcvWalkerContainer)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bnvWalker)

        bottomNavigation.setupWithNavController(navController)
    }
}