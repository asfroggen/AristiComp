package com.esaudev.aristicomp.auth.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_BAD_EMAIL
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_EMAIL_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_PASSWORD_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_UNKNOWN
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_USER_NOT_EXISTS
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_WRONG_PASSWORD
import com.esaudev.aristicomp.auth.utils.AuthConstants.WALKER_USER
import com.esaudev.aristicomp.databinding.FragmentLoginBinding
import com.esaudev.aristicomp.extensions.gone
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.visible
import com.esaudev.aristicomp.owner.OwnerActivity
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.walker.WalkerActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    @InternalCoroutinesApi
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
        return FragmentLoginBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initModeControl()
        initTextListeners()
        initClickListeners()
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
            etEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text?.toString().orEmpty())
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text?.toString().orEmpty())
            }
        }
    }

    private fun initClickListeners(){
        with(binding){
            mbPasswordForgotten.setOnClickListener {
                findNavController().navigate(R.id.toForgotPassword)
            }
            mbSignUp.setOnClickListener {
                findNavController().navigate(R.id.toSignUp)
            }
            mbLogin.setOnClickListener {
                viewModel.onLoginButtonClicked()
            }
        }
    }

    private fun processViewState(viewState: LoginViewState) {

        if (viewState.isUserOwner){
            setOwnerMode()
        } else {
            setWalkerMode()
        }

        if (viewState.showProgressBar){
            with(binding){
                pbLogin.visibility = View.VISIBLE
                mbLogin.isEnabled = false
                mbLogin.text = ""
            }
        } else {
            with(binding){
                pbLogin.visibility = View.GONE
                mbLogin.isEnabled = true
                mbLogin.text = getString(R.string.login__login_button)
            }
        }

        if (viewState.userLoggedSuccessfully){
            if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true){
                if (viewState.userType != Session.USER_LOGGED.type){
                    showSnackBar(getString(R.string.login__error_no_user_type))
                    viewModel.actionReset()
                } else {
                   manageUserLogin(viewState)
                }
            } else {
                showSnackBar(getString(R.string.login__error_email_not_verified))
                viewModel.actionReset()
            }

        }

        if (viewState.showLoginError){
            showSnackBar(getLoginError(viewState.loginError?: getString(R.string.login__error_unknown)))
            viewModel.actionReset()
        }
    }

    private fun getLoginError(error: String): String {
        return when(error){
            LOGIN_ERROR_WRONG_PASSWORD -> getString(R.string.login__error_wrong_password)
            LOGIN_ERROR_USER_NOT_EXISTS -> getString(R.string.login__error_user_not_exists)
            LOGIN_ERROR_UNKNOWN -> getString(R.string.login__error_unknown)
            LOGIN_ERROR_EMAIL_EMPTY -> getString(R.string.login__error_email_empty)
            LOGIN_ERROR_PASSWORD_EMPTY -> getString(R.string.login__error_password_empty)
            LOGIN_ERROR_BAD_EMAIL -> getString(R.string.login__error_bad_email)
            else -> getString(R.string.login__error_unknown)
        }
    }

    private fun manageUserLogin(viewState: LoginViewState){
        // Save user credentials in shared preferences
        sharedPrefs.edit().putString(Constants.SHARED_EMAIL, Session.USER_LOGGED.email).apply()
        sharedPrefs.edit().putString(Constants.SHARED_PASSWORD, viewState.password).apply()

        // Navigate user to main activity (Walker/Owner)
        if (Session.USER_LOGGED.type == WALKER_USER){
            startActivity(Intent(requireContext(), WalkerActivity::class.java))
            activity?.finish()
        } else {
            startActivity(Intent(requireContext(), OwnerActivity::class.java))
            activity?.finish()
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