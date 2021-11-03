package com.esaudev.aristicomp.auth.redux.middlewares

import com.esaudev.aristicomp.auth.redux.framework.Middleware
import com.esaudev.aristicomp.auth.redux.framework.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.redux.actions.LoginAction
import com.esaudev.aristicomp.auth.ui.login.LoginViewState
import javax.inject.Inject


class LoginNetworkMiddleware @Inject constructor(
    private val loginRepository: AuthRepository
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

                if (currentState.password.isEmpty()) {
                    store.dispatch(LoginAction.InvalidPasswordSubmitted)
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

        val response = loginRepository.login(
            email = currentState.email,
            password = currentState.password,
        )

        if (response.isSuccessful) {

            val userResponse = loginRepository.getUserData()

            if (userResponse.isSuccessful){
                store.dispatch(LoginAction.LoginCompleted)
            } else {
                store.dispatch(LoginAction.LoginFailed(userResponse.error))
            }
        } else {
            store.dispatch(LoginAction.LoginFailed(response.error))
        }
    }
}