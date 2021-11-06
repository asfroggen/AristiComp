package com.esaudev.aristicomp.owner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.databinding.ActivityOwnerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerActivity : AppCompatActivity() {

    private var _binding : ActivityOwnerBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fcvOwnerContainer)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bnvOwner)

        bottomNavigation.setupWithNavController(navController)

    }
}