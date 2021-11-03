package com.esaudev.aristicomp.auth.ui.forgot_password.middleware

import com.esaudev.aristicomp.auth.redux.Middleware
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.forgot_password.PasswordForgotViewState
import com.esaudev.aristicomp.auth.ui.forgot_password.actions.PasswordForgotAction
import com.esaudev.aristicomp.auth.ui.login.actions.LoginAction
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_EMAIL_EMPTY
import com.esaudev.aristicomp.auth.utils.AuthConstants.FORGOT_ERROR_GENERAL
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PasswordForgotNetworkMiddleware @Inject constructor(
    private val loginRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : Middleware<PasswordForgotViewState, PasswordForgotAction> {

    override suspend fun process(
        action: PasswordForgotAction,
        currentState: PasswordForgotViewState,
        store: Store<PasswordForgotViewState, PasswordForgotAction>
    ) {
        when(action) {
            is PasswordForgotAction.SendLinkButtonClicked -> {
                if (currentState.email.isEmpty()) {
                    store.dispatch(PasswordForgotAction.SendLinkFailed(FORGOT_ERROR_EMAIL_EMPTY))
                    return
                }
                sendPasswordLink(store, currentState)
            }
        }
    }

    private suspend fun sendPasswordLink(
        store: Store<PasswordForgotViewState, PasswordForgotAction>,
        currentState: PasswordForgotViewState
    ) {
        store.dispatch(PasswordForgotAction.SendLinkStarted)
        try {
            var isSuccessful = false
            firebaseAuth.sendPasswordResetEmail(currentState.email)
                .addOnCompleteListener {task ->
                    isSuccessful = task.isSuccessful
                }.await()

            if (isSuccessful){
                store.dispatch(PasswordForgotAction.SendLinkCompleted)
            } else {
                store.dispatch(PasswordForgotAction.SendLinkFailed(FORGOT_ERROR_GENERAL))
            }
        } catch (e: Exception){
            store.dispatch(PasswordForgotAction.SendLinkFailed(e.message.toString()))
        }

    }

}