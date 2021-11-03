package com.esaudev.aristicomp.auth.ui.sign_up

import com.esaudev.aristicomp.auth.models.User
import com.esaudev.aristicomp.auth.redux.framework.State

data class SignUpViewState (
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confPassword: String = "",
    val signUpError: String? = null,
    val signUpSuccess: Boolean = false,
    val showProgressBar: Boolean = false,
    val showSignUpError: Boolean = false,
    val isUserOwner: Boolean = false,
    val user: User = User()
): State