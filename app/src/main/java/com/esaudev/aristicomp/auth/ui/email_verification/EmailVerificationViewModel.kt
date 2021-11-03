package com.esaudev.aristicomp.auth.ui.email_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.email_verification.actions.EmailVerificationAction
import com.esaudev.aristicomp.auth.ui.email_verification.actions.EmailVerificationReducer
import com.esaudev.aristicomp.auth.ui.email_verification.middleware.EmailVerificationNetworkMiddleware
import com.esaudev.aristicomp.auth.ui.login.middleware.DebuggingMiddleware
import com.esaudev.aristicomp.auth.ui.sign_up.actions.SignUpAction
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val loginRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val store= Store(
        initialState = EmailVerificationViewState(),
        reducer = EmailVerificationReducer(),
        middlewares = listOf(
            DebuggingMiddleware(),
            EmailVerificationNetworkMiddleware(loginRepository)
        )
    )

    val viewState: StateFlow<EmailVerificationViewState> = store.state

    fun setViewState(email: String, password: String){
        val action = EmailVerificationAction.GetUserCredentials(email, password)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    private val _resendCounter: MutableLiveData<Int> = MutableLiveData()
    val resendCounter: LiveData<Int>
        get() = _resendCounter

    fun restartCounter(){
        viewModelScope.launch {
            var delay = 9
            while (delay>0){
                delay -= 1
                _resendCounter.value = delay
                delay(1000)
            }
        }
    }

    fun onResendButtonClicked(){
        val action = EmailVerificationAction.ResendButtonClicked

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun initializeCounter(){
        val action = EmailVerificationAction.CounterInitialized

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun emailVerified(){
        val action = EmailVerificationAction.EmailVerified

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun getUserData(){
        val action = EmailVerificationAction.GetUserData

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun actionReset(){
        val action = EmailVerificationAction.ActionCompleted

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    private val _verificationStatus: MutableLiveData<Boolean> = MutableLiveData()
    val verificationStatus: LiveData<Boolean>
        get() = _verificationStatus

    fun getVerificationStatus(){
        viewModelScope.launch {
            while (true){
                _verificationStatus.value = verifyEmailIsVerified()
                delay(1000)
            }
        }
    }

    private suspend fun verifyEmailIsVerified(): Boolean{
        firebaseAuth.currentUser?.reload()?.await()
        return firebaseAuth.currentUser?.isEmailVerified ?: false
    }


}