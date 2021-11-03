package com.esaudev.aristicomp.auth.redux.actions

import com.esaudev.aristicomp.auth.redux.framework.Action

sealed class PasswordForgotAction: Action {
    data class EmailChanged(val newEmail: String): PasswordForgotAction()
    data class SendLinkFailed(val sendLinkError: String): PasswordForgotAction()
    object SendLinkStarted: PasswordForgotAction()
    object SendLinkCompleted: PasswordForgotAction()
    object SendLinkButtonClicked: PasswordForgotAction()
    object ActionCompleted: PasswordForgotAction()
}