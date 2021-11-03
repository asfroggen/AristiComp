package com.esaudev.aristicomp.auth.ui.forgot_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.utils.AuthConstants
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_BAD_EMAIL
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_EMAIL_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_EMAIL_NOT_FOUND
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_GENERAL
import com.esaudev.aristicomp.databinding.FragmentForgotPasswordBinding
import com.esaudev.aristicomp.utils.hideKeyboard
import com.esaudev.aristicomp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PasswordForgotFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding: FragmentForgotPasswordBinding
        get() = _binding!!

    private val viewModel: PasswordForgotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Whenever the view is resumed, subscribe to our viewmodel's view state StateFlow
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
        return FragmentForgotPasswordBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextListeners()
        initListeners()
    }

    private fun initTextListeners(){
        with(binding){
            etEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text?.toString().orEmpty())
            }
        }
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener {
                activity?.onBackPressed()
            }
            mbRecoverPassword.setOnClickListener {
                hideKeyboard()
                viewModel.onSendLinkButtonClicked()
            }
        }
    }

    private fun processViewState(viewState: PasswordForgotViewState) {
        if (viewState.showProgressBar) {
            with(binding) {
                pbRecoverPassword.visibility = View.VISIBLE
                mbRecoverPassword.isEnabled = false
                mbRecoverPassword.text = ""
            }
        } else {
            with(binding) {
                pbRecoverPassword.visibility = View.GONE
                mbRecoverPassword.isEnabled = true
                mbRecoverPassword.text = getString(R.string.forgot_password__recover_button)
            }
        }
        if (viewState.linkSent){
            showSnackBar(getString(R.string.forgot_password__email_sent))
            activity?.onBackPressed()
        }

        if (viewState.showDisclaimer){
            showSnackBar(getSendLinkError(viewState.sendLinkError))
            viewModel.actionReset()
        }
    }

    private fun getSendLinkError(error: String): String{
        return when(error){
            FORGOT_ERROR_BAD_EMAIL -> getString(R.string.forgot_password__bad_email_error)
            FORGOT_ERROR_EMAIL_NOT_FOUND -> getString(R.string.forgot_password__general_error)
            FORGOT_ERROR_GENERAL -> getString(R.string.forgot_password__general_error)
            FORGOT_ERROR_EMAIL_EMPTY -> getString(R.string.forgot_password__empty_email_error)
            else -> getString(R.string.forgot_password__unknown_error)
        }
    }

}