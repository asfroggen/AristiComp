package com.esaudev.aristicomp.auth.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.auth.redux.framework.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.redux.actions.LoginAction
import com.esaudev.aristicomp.auth.redux.reducers.LoginReducer
import com.esaudev.aristicomp.auth.redux.middlewares.DebuggingMiddleware
import com.esaudev.aristicomp.auth.redux.middlewares.LoginNetworkMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [LoginViewModel] is responsible for controlling the UI logic of the login screen. It will
 * listen for text changes and button clicks, and update the UI state accordingly and expose that so
 * the View can update.
 *
 * Whenever a view action occurs, such as [onEmailChanged] or [onLoginButtonClicked], proxy the
 * corresponding [LoginAction] to our [store].
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: AuthRepository
) : ViewModel() {

    private val store = Store(
        initialState = LoginViewState(),
        reducer = LoginReducer(),
        middlewares = listOf(
            DebuggingMiddleware(),
            LoginNetworkMiddleware(loginRepository),
        )
    )

    val viewState: StateFlow<LoginViewState> = store.state

    fun onEmailChanged(newEmail: String) {
        val action = LoginAction.EmailChanged(newEmail)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        val action = LoginAction.PasswordChanged(newPassword)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onLoginButtonClicked() {
        val action = LoginAction.LoginButtonClicked

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onModeChanged(){
        val action = LoginAction.ModeChanged

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun actionReset(){
        val action = LoginAction.ActionCompleted

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}