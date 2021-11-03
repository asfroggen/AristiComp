package com.esaudev.aristicomp.walker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.databinding.ActivityWalkerBinding

class WalkerActivity : AppCompatActivity() {

    private var _binding : ActivityWalkerBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWalkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPrueba.text = Session.USER_LOGGED.toString()
    }
}