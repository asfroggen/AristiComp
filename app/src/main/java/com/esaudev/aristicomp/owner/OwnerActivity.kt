package com.esaudev.aristicomp.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.models.Session
import com.esaudev.aristicomp.databinding.ActivityOwnerBinding
import com.esaudev.aristicomp.databinding.ActivityWalkerBinding

class OwnerActivity : AppCompatActivity() {

    private var _binding : ActivityOwnerBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPrueba.text = Session.USER_LOGGED.toString()
    }
}