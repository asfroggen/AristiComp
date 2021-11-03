package com.esaudev.aristicomp.auth.ui.sign_up.actions

import com.esaudev.aristicomp.auth.redux.Reducer
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_EMAIL_INVALID
import com.esaudev.aristicomp.auth.utils.AuthConstants.SIGN_UP_ERROR_NAME_EMPTY
import com.esaudev.aristicomp.auth.ui.sign_up.SignUpViewState

class SignUpReducer : Reducer<SignUpViewState, SignUpAction> {
    override fun reduce(currentState: SignUpViewState, action: SignUpAction): SignUpViewState {
        return when(action) {
            is SignUpAction.NameChanged -> {
                stateWithNewName(currentState, action)
            }
            is SignUpAction.EmailChanged -> {
                stateWithNewEmail(currentState, action)
            }
            is SignUpAction.PasswordChanged -> {
                stateWithNewPassword(currentState, action)
            }
            is SignUpAction.PasswordConfirmationChanged -> {
                stateWithNewConfPassword(currentState, action)
            }
            is SignUpAction.SignUpFailed -> {
                stateAfterSignUpFailed(currentState, action)
            }
            is SignUpAction.InvalidPasswordSubmitted -> {
                stateWithInvalidPassword(currentState, action)
            }
            is SignUpAction.SignUpCompleted -> {
                stateAfterSignUpCompleted(currentState, action)
            }
            SignUpAction.SignUpStarted -> {
                stateAfterSignUpStarted(currentState)
            }

            SignUpAction.ModeChanged -> {
                stateAfterModeChanged(currentState)
            }
            SignUpAction.InvalidNameSubmitted -> {
                stateWithInvalidName(currentState)
            }
            SignUpAction.InvalidEmailSubmitted -> {
                stateWithInvalidEmail(currentState)
            }
            SignUpAction.ActionCompleted -> {
                stateAfterActionShowed(currentState)
            }

            else -> currentState

        }
    }

    private fun stateWithNewName(
        currentState: SignUpViewState,
        action: SignUpAction.NameChanged
    ) = currentState.copy(
        name = action.newName
    )

    private fun stateWithNewEmail(
        currentState: SignUpViewState,
        action: SignUpAction.EmailChanged
    ) = currentState.copy(
        email = action.newEmail
    )

    private fun stateWithNewPassword(
        currentState: SignUpViewState,
        action: SignUpAction.PasswordChanged
    ) = currentState.copy(
        password = action.newPassword
    )

    private fun stateWithNewConfPassword(
        currentState: SignUpViewState,
        action: SignUpAction.PasswordConfirmationChanged
    ) = currentState.copy(
        confPassword = action.newConfPassword
    )

    private fun stateAfterSignUpFailed(
        currentState: SignUpViewState,
        action: SignUpAction.SignUpFailed
    ) = currentState.copy(
        showProgressBar = false,
        showSignUpError = true,
        signUpError = action.signUpError
    )

    private fun stateWithInvalidPassword(
        currentState: SignUpViewState,
        action: SignUpAction.InvalidPasswordSubmitted
    ) = currentState.copy(
        showSignUpError = true,
        signUpError = action.passwordError
    )

    private fun stateAfterSignUpCompleted(
        currentState: SignUpViewState,
        action: SignUpAction.SignUpCompleted
    ) = currentState.copy(
        showProgressBar = false,
        signUpSuccess = true,
        user = action.user
    )

    private fun stateAfterSignUpStarted(
        currentState: SignUpViewState
    ) = currentState.copy(
        showProgressBar = true
    )

    private fun stateAfterModeChanged(
        currentState: SignUpViewState
    ) = currentState.copy(
        isUserOwner = !currentState.isUserOwner
    )

    private fun stateWithInvalidName(
        currentState: SignUpViewState
    ) = currentState.copy(
        showSignUpError = true,
        signUpError = SIGN_UP_ERROR_NAME_EMPTY
    )

    private fun stateWithInvalidEmail(
        currentState: SignUpViewState
    ) = currentState.copy(
        showSignUpError = true,
        signUpError = SIGN_UP_ERROR_EMAIL_INVALID
    )

    private fun stateAfterActionShowed(
        currentState: SignUpViewState
    ) = currentState.copy(
        showSignUpError = false,
        signUpSuccess = false
    )
}