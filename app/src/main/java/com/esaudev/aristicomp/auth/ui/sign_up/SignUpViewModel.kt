package com.esaudev.aristicomp.auth.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.auth.redux.framework.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.redux.middlewares.DebuggingMiddleware
import com.esaudev.aristicomp.auth.redux.actions.SignUpAction
import com.esaudev.aristicomp.auth.redux.reducers.SignUpReducer
import com.esaudev.aristicomp.auth.redux.middlewares.SignUpNetworkMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val loginRepository: AuthRepository
): ViewModel() {

    private val store = Store(
        initialState = SignUpViewState(),
        reducer = SignUpReducer(),
        middlewares = listOf(
            DebuggingMiddleware(),
            SignUpNetworkMiddleware(loginRepository)
        )
    )

    val viewState: StateFlow<SignUpViewState> = store.state

    fun onNameChanged(newName: String) {
        val action = SignUpAction.NameChanged(newName)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onEmailChanged(newEmail: String) {
        val action = SignUpAction.EmailChanged(newEmail)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        val action = SignUpAction.PasswordChanged(newPassword)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onConfPasswordChanged(newConfPassword: String) {
        val action = SignUpAction.PasswordConfirmationChanged(newConfPassword)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onSignUpButtonClicked() {
        val action = SignUpAction.SignUpButtonClicked

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onModeChanged(){
        val action = SignUpAction.ModeChanged

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun actionReset(){
        val action = SignUpAction.ActionCompleted

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}