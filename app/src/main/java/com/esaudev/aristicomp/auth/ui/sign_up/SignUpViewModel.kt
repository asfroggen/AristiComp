package com.esaudev.aristicomp.auth.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.ui.login.actions.LoginAction
import com.esaudev.aristicomp.auth.ui.login.middleware.DebuggingMiddleware
import com.esaudev.aristicomp.auth.ui.sign_up.actions.SignUpAction
import com.esaudev.aristicomp.auth.ui.sign_up.actions.SignUpReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): ViewModel() {

    private val store = Store(
        initialState = SignUpViewState(),
        reducer = SignUpReducer(),
        middlewares = listOf(
            DebuggingMiddleware()
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