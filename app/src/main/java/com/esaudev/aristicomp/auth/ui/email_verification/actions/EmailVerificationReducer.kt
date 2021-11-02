package com.esaudev.aristicomp.auth.ui.email_verification.actions

import com.esaudev.aristicomp.auth.redux.Reducer
import com.esaudev.aristicomp.auth.ui.email_verification.EmailVerificationViewState
import com.esaudev.aristicomp.auth.ui.login.LoginViewState
import com.esaudev.aristicomp.auth.ui.login.actions.LoginAction

/**
 * This reducer is responsible for handling any [EmailVerificationAction], and using that to create
 * a new [EmailVerificationViewState].
 */
class EmailVerificationReducer: Reducer<EmailVerificationViewState, EmailVerificationAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(
        currentState: EmailVerificationViewState,
        action: EmailVerificationAction
    ): EmailVerificationViewState {
        return when(action) {
            EmailVerificationAction.ConfirmationEmailSent -> {
                stateAfterConfEmailSent(currentState)
            }
            EmailVerificationAction.EmailVerified -> {
                stateWithEmailVerified(currentState)
            }
            EmailVerificationAction.ActionCompleted -> {
                stateAfterActionCompleted(currentState)
            }
            EmailVerificationAction.CounterInitialized -> {
                stateAfterCounterInitialized(currentState)
            }
            else -> currentState
        }
    }

    private fun stateAfterConfEmailSent(
        currentState: EmailVerificationViewState
    ) = currentState.copy(
        resetCounter = true
    )

    private fun stateWithEmailVerified(
        currentState: EmailVerificationViewState
    ) = currentState.copy(
        isEmailVerified = true
    )

    private fun stateAfterActionCompleted(
        currentState: EmailVerificationViewState
    ) = currentState.copy(
        resetCounter = false,
        showDisclaimer = false,
        isEmailVerified = false
    )

    private fun stateAfterCounterInitialized(
        currentState: EmailVerificationViewState
    ) = currentState.copy(
        isCounterInitialized = true
    )

}