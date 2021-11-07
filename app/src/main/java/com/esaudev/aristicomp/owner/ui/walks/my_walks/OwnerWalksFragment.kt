package com.esaudev.aristicomp.owner.ui.walks.my_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerWalksFragment : Fragment() {

    private var _binding: FragmentOwnerWalksBinding? = null
    private val binding: FragmentOwnerWalksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerWalksBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners(){
        with(binding){
            fabNewWalk.setOnClickListener {
                findNavController().navigate(R.id.ownerWalksToNewWalkFragment)
            }
        }
    }

}