package com.esaudev.aristicomp.auth.ui.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_EMAIL_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_EMAIL_INVALID
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_NAME_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_PASSWORDS_NOT_MATCH
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_PASSWORD_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_PASSWORD_INSECURE
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_USER_ALREADY_EXISTS
import com.esaudev.aristicomp.databinding.FragmentSignUpBinding
import com.esaudev.aristicomp.utils.Constants.USER_BUNDLE
import com.esaudev.aristicomp.utils.Constants.USER_PASSWORD_BUNDLE
import com.esaudev.aristicomp.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

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
        return FragmentSignUpBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initModeControl()
        initTextListeners()
        initListeners()
    }

    private fun initModeControl(){
        with(binding){
            llOwner.setOnClickListener {
                viewModel.onModeChanged()
            }

            llWalker.setOnClickListener {
                viewModel.onModeChanged()
            }
        }
    }

    private fun initTextListeners(){
        with(binding){
            etName.doOnTextChanged { text, _, _, _ ->
                viewModel.onNameChanged(text?.toString().orEmpty())
            }
            etEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text?.toString().orEmpty())
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text?.toString().orEmpty())
            }
            etConfirmPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onConfPasswordChanged(text?.toString().orEmpty())
            }
        }
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener {
                activity?.onBackPressed()
            }

            mbSignUp.setOnClickListener {
                viewModel.onSignUpButtonClicked()
            }

            mbLogin.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setOwnerMode(){
        with(binding){
            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.trasnparent)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun setWalkerMode(){
        with(binding){
            llOwner.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.trasnparent)
            tvOwner.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_text))

            llWalker.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            tvWalker.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    private fun processViewState(viewState: SignUpViewState) {

        if (viewState.isUserOwner){
            setOwnerMode()
        } else {
            setWalkerMode()
        }

        if (viewState.showProgressBar){
            with(binding){
                pbSignUp.visibility = View.VISIBLE
                mbSignUp.isEnabled = false
                mbSignUp.text = ""
            }
        } else {
            with(binding){
                pbSignUp.visibility = View.GONE
                mbSignUp.isEnabled = true
                mbSignUp.text = getString(R.string.login__login_button)
            }
        }

        if (viewState.showSignUpError){
            showSnackBar(getSignUpError(viewState.signUpError?: getString(R.string.login__error_unknown)))
            viewModel.actionReset()
        }

        if (viewState.signUpSuccess){
            findNavController().navigate(R.id.toEmailVerification, bundleOf(USER_BUNDLE to viewState.user, USER_PASSWORD_BUNDLE to viewState.password))
            viewModel.actionReset()
        }
    }

    private fun getSignUpError(error: String): String {
        return when(error){
            SIGN_UP_ERROR_USER_ALREADY_EXISTS -> getString(R.string.signup__error_user_already_exists)
            SIGN_UP_ERROR_NAME_EMPTY -> getString(R.string.signup__error_name_empty)
            SIGN_UP_ERROR_EMAIL_EMPTY -> getString(R.string.signup__error_email_empty)
            SIGN_UP_ERROR_PASSWORD_EMPTY -> getString(R.string.signup__error_password_empty)
            SIGN_UP_ERROR_EMAIL_INVALID -> getString(R.string.signup__error_email_invalid)
            SIGN_UP_ERROR_PASSWORDS_NOT_MATCH -> getString(R.string.signup__error_passwords_not_match)
            SIGN_UP_ERROR_PASSWORD_INSECURE -> getString(R.string.signup__error_password_insecure)
            else -> getString(R.string.signup__error_unknown)
        }
    }


}