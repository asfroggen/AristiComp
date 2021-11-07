package com.esaudev.aristicomp.owner.ui.walks.new_walk

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewWalkBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding
import com.esaudev.aristicomp.extensions.goneToBottom
import com.esaudev.aristicomp.extensions.visibleFromBottom
import kotlinx.android.synthetic.main.activity_owner.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
        }
    }

    private fun viewBottomNav(visibility: Boolean){
        if (visibility){
            requireActivity().bnvOwner.visibleFromBottom()
        } else {
            requireActivity().bnvOwner.goneToBottom()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewBottomNav(false)
    }

    override fun onDetach() {
        super.onDetach()
        viewBottomNav(true)
    }
}