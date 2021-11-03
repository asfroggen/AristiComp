package com.esaudev.aristicomp.auth.ui.forgot_password

import com.esaudev.aristicomp.auth.redux.State

data class PasswordForgotViewState (
    val email: String = "",
    val linkSent: Boolean = false,
    val sendLinkError: String = "",
    val showProgressBar: Boolean = false,
    val showDisclaimer: Boolean = false,
): State