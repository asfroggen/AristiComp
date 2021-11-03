package com.esaudev.aristicomp.auth.ui.login

import com.esaudev.aristicomp.auth.redux.State
import com.esaudev.aristicomp.auth.utils.AuthConstants.WALKER_USER

/**
 * An implementation of [State] that describes the configuration of the login screen at a given time.
 */
data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val loginError: String? = null,
    val userType: String = WALKER_USER,
    val showLoginError: Boolean = false,
    val showProgressBar: Boolean = false,
    val isUserOwner: Boolean = false,
    val userLoggedSuccessfully: Boolean = false
) : State