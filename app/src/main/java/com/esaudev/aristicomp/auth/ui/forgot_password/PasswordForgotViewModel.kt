package com.esaudev.aristicomp.auth.ui.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.auth.redux.framework.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.redux.actions.PasswordForgotAction
import com.esaudev.aristicomp.auth.redux.reducers.PasswordForgotReducer
import com.esaudev.aristicomp.auth.redux.middlewares.PasswordForgotNetworkMiddleware
import com.esaudev.aristicomp.auth.redux.middlewares.DebuggingMiddleware
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordForgotViewModel @Inject constructor(
    private val loginRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val store = Store(
        initialState = PasswordForgotViewState(),
        reducer = PasswordForgotReducer(),
        middlewares = listOf(
            DebuggingMiddleware(),
            PasswordForgotNetworkMiddleware(loginRepository, firebaseAuth)
        )
    )

    val viewState: StateFlow<PasswordForgotViewState> = store.state

    fun onEmailChanged(newEmail: String) {
        val action = PasswordForgotAction.EmailChanged(newEmail)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun onSendLinkButtonClicked() {
        val action = PasswordForgotAction.SendLinkButtonClicked

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun actionReset(){
        val action = PasswordForgotAction.ActionCompleted

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}