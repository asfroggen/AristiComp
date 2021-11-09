package com.esaudev.aristicomp.walker.ui.my_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentWalkerSearchBinding
import com.esaudev.aristicomp.databinding.FragmentWalkerWalksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkerWalksFragment : Fragment() {

    private var _binding: FragmentWalkerWalksBinding? = null
    private val binding: FragmentWalkerWalksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWalkerWalksBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

}