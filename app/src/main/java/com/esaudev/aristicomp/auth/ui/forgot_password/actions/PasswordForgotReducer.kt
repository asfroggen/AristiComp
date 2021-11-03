package com.esaudev.aristicomp.auth.ui.forgot_password.actions

import com.esaudev.aristicomp.auth.redux.Reducer
import com.esaudev.aristicomp.auth.ui.forgot_password.PasswordForgotViewState
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_GENERAL

/**
 * This reducer is responsible for handling any [PasswordForgotAction], and using that to create
 * a new [PasswordForgotViewState].
 */
class PasswordForgotReducer: Reducer<PasswordForgotViewState, PasswordForgotAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(
        currentState: PasswordForgotViewState,
        action: PasswordForgotAction
    ): PasswordForgotViewState {
        return when(action) {
            is PasswordForgotAction.EmailChanged -> {
                stateWithNewEmail(currentState, action)
            }
            is PasswordForgotAction.SendLinkFailed -> {
                stateAfterSendLinkFailed(currentState, action)
            }
            PasswordForgotAction.SendLinkStarted -> {
                stateAfterSendLinkStarted(currentState)
            }
            PasswordForgotAction.SendLinkCompleted -> {
                stateAfterSendLinkCompleted(currentState)
            }
            PasswordForgotAction.ActionCompleted -> {
                stateAfterActionCompleted(currentState)
            }
            else -> currentState
        }
    }

    private fun stateWithNewEmail(
        currentState: PasswordForgotViewState,
        action: PasswordForgotAction.EmailChanged
    ) = currentState.copy(
        email = action.newEmail
    )

    private fun stateAfterSendLinkStarted(
        currentState: PasswordForgotViewState
    ) = currentState.copy(
        showProgressBar = true
    )

    private fun stateAfterSendLinkCompleted(
        currentState: PasswordForgotViewState
    ) = currentState.copy(
        showProgressBar = false,
        linkSent = true
    )

    private fun stateAfterSendLinkFailed(
        currentState: PasswordForgotViewState,
        action: PasswordForgotAction.SendLinkFailed
    ) = currentState.copy(
        showDisclaimer = true,
        sendLinkError = action.sendLinkError
    )

    private fun stateAfterActionCompleted(
        currentState: PasswordForgotViewState
    ) = currentState.copy(
        showDisclaimer = false,
        showProgressBar = false
    )

}