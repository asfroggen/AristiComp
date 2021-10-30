package com.esaudev.aristicomp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentLoginBinding
import com.esaudev.aristicomp.utils.gone
import com.esaudev.aristicomp.utils.visible

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentLoginBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initModeControl()
        initListeners()
    }

    private fun initModeControl(){
        with(binding){
            llOwner.setOnClickListener {
                setOwnerMode()
            }

            llWalker.setOnClickListener {
                setWalkerMode()
            }
        }
    }

    private fun initListeners(){
        with(binding){
            mbPasswordForgotten.setOnClickListener {
                findNavController().navigate(R.id.toForgotPassword)
            }
            mbSignUp.setOnClickListener {
                findNavController().navigate(R.id.toSignUp)
            }
        }
    }

    private fun setOwnerMode(){
        with(binding){
            avWalker.gone()

            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.trasnparent)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            avOwner.visible()
        }
    }

    private fun setWalkerMode(){
        with(binding){
            avOwner.gone()

            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.trasnparent)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            avWalker.visible()
        }
    }


}