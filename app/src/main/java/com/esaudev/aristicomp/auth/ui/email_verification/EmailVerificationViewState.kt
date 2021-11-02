package com.esaudev.aristicomp.auth.ui.email_verification

import com.esaudev.aristicomp.auth.redux.State

data class EmailVerificationViewState (
    val isCounterInitialized: Boolean = false,
    val resetCounter: Boolean = false,
    val showDisclaimer: Boolean = false,
    val isEmailVerified: Boolean = false
): State