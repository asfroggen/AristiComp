package com.esaudev.aristicomp.auth.ui.email_verification.actions

import com.esaudev.aristicomp.auth.redux.Action

sealed class EmailVerificationAction: Action {
    object CounterInitialized: EmailVerificationAction()
    object EmailVerified: EmailVerificationAction()
    object ConfirmationEmailSent: EmailVerificationAction()
    object ResendButtonClicked: EmailVerificationAction()
    object ActionCompleted: EmailVerificationAction()
}