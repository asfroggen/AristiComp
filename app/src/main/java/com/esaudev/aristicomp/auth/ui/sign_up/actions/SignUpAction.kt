package com.esaudev.aristicomp.auth.ui.sign_up.actions

import com.esaudev.aristicomp.auth.redux.Action

sealed class SignUpAction: Action {
    data class NameChanged(val newName: String) : SignUpAction()
    data class EmailChanged(val newEmail: String) : SignUpAction()
    data class PasswordChanged(val newPassword: String) : SignUpAction()
    data class PasswordConfirmationChanged(val newConfPassword: String) : SignUpAction()
    data class SignUpFailed(val signUpError: String) : SignUpAction()
    object ModeChanged : SignUpAction()
    object SignUpButtonClicked: SignUpAction()
    object SignUpStarted : SignUpAction()
    object SignUpCompleted : SignUpAction()
    object InvalidNameSubmitted : SignUpAction()
    object InvalidEmailSubmitted : SignUpAction()
    object InvalidPasswordSubmitted : SignUpAction()
    object ActionCompleted : SignUpAction() // Use only when we trigger an action that show something eg. Toasters, Snackbars
}
