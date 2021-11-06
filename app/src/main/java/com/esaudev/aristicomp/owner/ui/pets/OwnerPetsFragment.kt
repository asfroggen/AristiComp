package com.esaudev.aristicomp.owner.ui.pets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerPetsBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding

class OwnerPetsFragment : Fragment() {

    private var _binding: FragmentOwnerPetsBinding? = null
    private val binding: FragmentOwnerPetsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerPetsBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners(){
        binding.fabNewPet.setOnClickListener {
            findNavController().navigate(R.id.ownerPetsToNewPetFragment)
        }
    }

}