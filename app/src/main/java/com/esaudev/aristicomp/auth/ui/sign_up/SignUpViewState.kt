package com.esaudev.aristicomp.auth.ui.sign_up

import com.esaudev.aristicomp.auth.redux.State

data class SignUpViewState (
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confPassword: String = "",
    val signUpError: String? = null,
    val showProgressBar: Boolean = false,
    val showSignUpError: Boolean = false,
    val showOwnerMode: Boolean = false
): State