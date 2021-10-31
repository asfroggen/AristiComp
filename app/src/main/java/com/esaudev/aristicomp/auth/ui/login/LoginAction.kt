package com.esaudev.aristicomp.auth.ui.login

import com.esaudev.aristicomp.auth.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class LoginAction : Action {
    data class EmailChanged(val newEmail: String) : LoginAction()
    data class PasswordChanged(val newPassword: String) : LoginAction()
    object LoginButtonClicked : LoginAction()
    object ForgotPasswordButtonClicked : LoginAction()
    object SignUpButtonClicked : LoginAction()
    object ModeChanged : LoginAction()
    object LoginStarted : LoginAction()
    object LoginCompleted : LoginAction()
    data class LoginFailed(val error: Throwable?) : LoginAction()
    object InvalidEmailSubmitted : LoginAction()
}
