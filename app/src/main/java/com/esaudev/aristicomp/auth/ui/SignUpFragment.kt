package com.esaudev.aristicomp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentForgotPasswordBinding
import com.esaudev.aristicomp.databinding.FragmentSignUpBinding
import com.esaudev.aristicomp.utils.gone
import com.esaudev.aristicomp.utils.visible

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentSignUpBinding
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
            mbBack.setOnClickListener {
                activity?.onBackPressed()
            }

            mbSignUp.setOnClickListener {
                findNavController().navigate(R.id.toEmailVerification)
            }

            mbLogin.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setOwnerMode(){
        with(binding){
            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun setWalkerMode(){
        with(binding){
            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }


}