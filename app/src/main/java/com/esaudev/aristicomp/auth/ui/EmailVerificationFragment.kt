package com.esaudev.aristicomp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.models.User
import com.esaudev.aristicomp.databinding.FragmentEmailVerificationBinding
import com.esaudev.aristicomp.databinding.FragmentSignUpBinding
import com.esaudev.aristicomp.utils.Constants.USER_BUNDLE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EmailVerificationFragment : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding: FragmentEmailVerificationBinding
        get() = _binding!!

    private var user: User? = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            user = it.getParcelable(USER_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentEmailVerificationBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView(){
        setupResendCounter()
        binding.tvTitle.text = getString(R.string.email_verification__title, user?.name?: "")
    }

    private fun initListeners(){
        with(binding){
            mbResend.setOnClickListener {
                setupResendCounter()
            }
            mbBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setupResendCounter(){
        var delay = 9
        binding.mbResend.isEnabled = false
        lifecycleScope.launch {
            while (delay>0){
                delay -= 1
                binding.mbResend.text = getString(R.string.email_verification__resend_counter, delay.toString())

                if (delay == 0){
                    binding.mbResend.isEnabled = true
                    binding.mbResend.text = getString(R.string.email_verification__send_link)
                }

                delay(1000)
            }
        }
    }

}