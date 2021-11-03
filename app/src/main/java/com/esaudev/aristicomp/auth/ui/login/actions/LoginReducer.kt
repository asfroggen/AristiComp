package com.esaudev.aristicomp.auth.ui.login.actions

import com.esaudev.aristicomp.auth.redux.Reducer
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_EMAIL_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_PASSWORD_EMPTY
import com.esaudev.aristicomp.auth.ui.login.LoginViewState
import com.esaudev.aristicomp.auth.utils.AuthConstants.LOGIN_ERROR_UNKNOWN
import com.esaudev.aristicomp.auth.utils.AuthConstants.OWNER_USER
import com.esaudev.aristicomp.auth.utils.AuthConstants.WALKER_USER

/**
 * This reducer is responsible for handling any [LoginAction], and using that to create
 * a new [LoginViewState].
 */
class LoginReducer : Reducer<LoginViewState, LoginAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(currentState: LoginViewState, action: LoginAction): LoginViewState {
        return when (action) {
            is LoginAction.EmailChanged -> {
                stateWithNewEmail(currentState, action)
            }
            is LoginAction.PasswordChanged -> {
                stateWithNewPassword(currentState, action)
            }
            LoginAction.LoginStarted -> {
                stateAfterLoginStarted(currentState)
            }
            LoginAction.LoginCompleted -> {
                stateAfterLoginCompleted(currentState)
            }
            LoginAction.ModeChanged -> {
               stateAfterModeChanged(currentState)
            }
            is LoginAction.LoginFailed -> {
                stateAfterLoginFailed(currentState, action)
            }
            LoginAction.InvalidEmailSubmitted -> {
                stateWithInvalidEmailError(currentState)
            }
            LoginAction.InvalidPasswordSubmitted -> {
                stateWithInvalidPasswordError(currentState)
            }
            LoginAction.ActionCompleted -> {
                stateAfterActionShowed(currentState)
            }
            else -> currentState
        }
    }

    private fun stateWithInvalidEmailError(currentState: LoginViewState) =
        currentState.copy(
            showLoginError = true,
            loginError = LOGIN_ERROR_EMAIL_EMPTY
        )

    private fun stateWithInvalidPasswordError(currentState: LoginViewState) =
        currentState.copy(
            showLoginError = true,
            loginError = LOGIN_ERROR_PASSWORD_EMPTY
        )

    private fun stateAfterLoginStarted(currentState: LoginViewState) =
        currentState.copy(
            showProgressBar = true,
        )

    private fun stateAfterLoginCompleted(currentState: LoginViewState) =
        currentState.copy(
            userLoggedSuccessfully = true,
            showProgressBar = false
        )

    private fun stateAfterModeChanged(currentState: LoginViewState): LoginViewState {
        val userType = !currentState.isUserOwner

        return currentState.copy(
            isUserOwner = userType,
            userType = if (userType) OWNER_USER else WALKER_USER
        )
    }


    private fun stateAfterLoginFailed(
        currentState: LoginViewState,
        action: LoginAction.LoginFailed
    ) = currentState.copy(
            showProgressBar = false,
            showLoginError = true,
            loginError = action.loginError
        )

    private fun stateAfterActionShowed(
        currentState: LoginViewState
    ) = currentState.copy(
        showLoginError = false,
        userLoggedSuccessfully = false
    )

    private fun stateWithNewPassword(
        currentState: LoginViewState,
        action: LoginAction.PasswordChanged
    ) = currentState.copy(
        password = action.newPassword
    )

    private fun stateWithNewEmail(
        currentState: LoginViewState,
        action: LoginAction.EmailChanged
    ) = currentState.copy(
        email = action.newEmail
    )
}