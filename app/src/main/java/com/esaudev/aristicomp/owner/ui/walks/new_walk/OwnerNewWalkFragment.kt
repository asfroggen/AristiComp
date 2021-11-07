package com.esaudev.aristicomp.owner.ui.walks.new_walk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewWalkBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding

class OwnerNewWalkFragment : Fragment() {

    private var _binding: FragmentOwnerNewWalkBinding? = null
    private val binding: FragmentOwnerNewWalkBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerNewWalkBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }



}