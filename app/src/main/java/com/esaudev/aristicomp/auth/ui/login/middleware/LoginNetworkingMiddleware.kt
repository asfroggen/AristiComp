package com.esaudev.aristicomp.auth.ui.login.middleware

import com.esaudev.aristicomp.auth.redux.Middleware
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.login.LoginAction
import com.esaudev.aristicomp.auth.ui.login.LoginViewState


class LoginNetworkingMiddleware(
    private val loginRepository: AuthRepository,
) : Middleware<LoginViewState, LoginAction> {

    override suspend fun process(
        action: LoginAction,
        currentState: LoginViewState,
        store: Store<LoginViewState, LoginAction>,
    ) {
        when (action) {
            is LoginAction.LoginButtonClicked -> {
                if (currentState.email.isEmpty()) {
                    store.dispatch(LoginAction.InvalidEmailSubmitted)
                    return
                }

                loginUser(store, currentState)
            }
        }
    }

    private suspend fun loginUser(
        store: Store<LoginViewState, LoginAction>,
        currentState: LoginViewState
    ) {
        store.dispatch(LoginAction.LoginStarted)

        val isSuccessful = loginRepository.login(
            email = currentState.email,
            password = currentState.password,
        )

        if (isSuccessful) {
            store.dispatch(LoginAction.LoginCompleted)
        } else {
            store.dispatch(LoginAction.LoginFailed(null))
        }
    }
}