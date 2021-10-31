package com.esaudev.aristicomp.auth.ui.login

import com.esaudev.aristicomp.auth.redux.State

/**
 * An implementation of [State] that describes the configuration of the login screen at a given time.
 */
data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val showProgressBar: Boolean = false,
    val showOwnerMode: Boolean = false
) : State