package com.esaudev.aristicomp.auth.ui.email_verification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.models.User
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.INFO_NOT_SET
import com.esaudev.aristicomp.databinding.FragmentEmailVerificationBinding
import com.esaudev.aristicomp.utils.Constants.USER_BUNDLE
import com.esaudev.aristicomp.utils.Constants.USER_PASSWORD_BUNDLE
import com.esaudev.aristicomp.walker.WalkerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailVerificationFragment : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding: FragmentEmailVerificationBinding
        get() = _binding!!

    private var user: User? = User()
    private var password: String? = ""

    private val viewModel: EmailVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            user = it.getParcelable(USER_BUNDLE)
            password = it.getString(USER_PASSWORD_BUNDLE)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
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
        initObservers()
        initListeners()
    }

    private fun initView(){
        if (user?.id != INFO_NOT_SET){
            viewModel.setViewState(user!!.email, password!!)
            viewModel.getVerificationStatus()
        }
        binding.tvTitle.text = getString(R.string.email_verification__title, user?.name?: "")
    }

    private fun initObservers(){
        viewModel.resendCounter.observe(viewLifecycleOwner, {
            processCounter(it)
        })

        viewModel.verificationStatus.observe(viewLifecycleOwner, { emailVerified ->
            if (emailVerified){
                viewModel.emailVerified()
            }
        })
    }

    private fun initListeners(){
        with(binding){
            mbResend.setOnClickListener {
                viewModel.onResendButtonClicked()
            }
            mbBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun processViewState(viewState: EmailVerificationViewState) {
        if (viewState.resetCounter){
            viewModel.restartCounter()
            viewModel.actionReset()
        }
        if (!viewState.isCounterInitialized){
            viewModel.initializeCounter()
            viewModel.restartCounter()
            viewModel.actionReset()
        }
        if (viewState.isEmailVerified){
            viewModel.getUserData()
        }
        if (viewState.userReadyToContinue){
            startActivity(Intent(requireContext(), WalkerActivity::class.java))
            activity?.finish()
        }
    }

    private fun processCounter(count: Int){
        if (count==0){
            binding.mbResend.isEnabled = true
            binding.mbResend.text = getString(R.string.email_verification__send_link)
        } else {
            binding.mbResend.isEnabled = false
            binding.mbResend.text = getString(R.string.email_verification__resend_counter, count.toString())
        }
    }

}