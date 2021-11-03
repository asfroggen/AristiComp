package com.esaudev.aristicomp.auth.ui.email_verification.middleware

import com.esaudev.aristicomp.auth.redux.Middleware
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.email_verification.EmailVerificationViewState
import com.esaudev.aristicomp.auth.ui.email_verification.actions.EmailVerificationAction
import com.esaudev.aristicomp.auth.ui.sign_up.actions.SignUpAction
import com.esaudev.aristicomp.utils.Constants.FIREBASE_USER_SIGNED
import javax.inject.Inject

class EmailVerificationNetworkMiddleware @Inject constructor(
    private val loginRepository: AuthRepository
) : Middleware<EmailVerificationViewState, EmailVerificationAction> {
    override suspend fun process(
        action: EmailVerificationAction,
        currentState: EmailVerificationViewState,
        store: Store<EmailVerificationViewState, EmailVerificationAction>
    ) {
        when(action){
            is EmailVerificationAction.ResendButtonClicked -> {

                resendVerificationEmail(store, currentState)
            }

            is EmailVerificationAction.GetUserData -> {

                getUserData(store)
            }
        }
    }

    private suspend fun resendVerificationEmail(
        store: Store<EmailVerificationViewState, EmailVerificationAction>,
        currentState: EmailVerificationViewState
    ) {

        FIREBASE_USER_SIGNED?.sendEmailVerification()
        store.dispatch(EmailVerificationAction.ConfirmationEmailSent)
    }

    private suspend fun getUserData(
        store: Store<EmailVerificationViewState, EmailVerificationAction>
    ) {
        val getUserResponse = loginRepository.getUserData()

        if (getUserResponse.isSuccessful){
            store.dispatch(EmailVerificationAction.GetUserDataCompleted)
        } else {
            store.dispatch(EmailVerificationAction.ActionCompleted)
        }
    }

}