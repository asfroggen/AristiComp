package com.esaudev.aristicomp.owner.ui.walks.my_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding


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

}