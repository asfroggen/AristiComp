package com.esaudev.aristicomp.auth.ui.email_verification

import com.esaudev.aristicomp.auth.redux.State

data class EmailVerificationViewState (
    val email: String = "",
    val password: String = "",
    val isCounterInitialized: Boolean = false,
    val resetCounter: Boolean = false,
    val showDisclaimer: Boolean = false,
    val isEmailVerified: Boolean = false,
    val userReadyToContinue: Boolean = false,
): State