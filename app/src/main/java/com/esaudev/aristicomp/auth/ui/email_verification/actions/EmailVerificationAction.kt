package com.esaudev.aristicomp.auth.ui.email_verification.actions

import com.esaudev.aristicomp.auth.redux.Action

sealed class EmailVerificationAction: Action {
    data class GetUserCredentials(val email: String, val password: String): EmailVerificationAction()
    object GetUserData: EmailVerificationAction()
    object GetUserDataCompleted: EmailVerificationAction()
    object CounterInitialized: EmailVerificationAction()
    object EmailVerified: EmailVerificationAction()
    object ConfirmationEmailSent: EmailVerificationAction()
    object ResendButtonClicked: EmailVerificationAction()
    object ActionCompleted: EmailVerificationAction()
}