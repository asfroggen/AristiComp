package com.esaudev.aristicomp.auth.ui.sign_up.middleware

import com.esaudev.aristicomp.auth.data.responses.SignUpResponse
import com.esaudev.aristicomp.auth.models.User
import com.esaudev.aristicomp.auth.redux.Middleware
import com.esaudev.aristicomp.auth.redux.Store
import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.EMAIL_ADDRESS_PATTERN
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.OWNER_USER
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.SIGN_UP_ERROR_PASSWORDS_NOT_MATCH
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.SIGN_UP_ERROR_PASSWORD_INSECURE
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.WALKER_USER
import com.esaudev.aristicomp.auth.ui.login.LoginViewState
import com.esaudev.aristicomp.auth.ui.sign_up.SignUpViewState
import com.esaudev.aristicomp.auth.ui.sign_up.actions.SignUpAction
import java.util.regex.Pattern
import javax.inject.Inject

class SignUpNetworkMiddleware @Inject constructor(
    private val loginRepository: AuthRepository
) : Middleware<SignUpViewState, SignUpAction> {

    override suspend fun process(
        action: SignUpAction,
        currentState: SignUpViewState,
        store: Store<SignUpViewState, SignUpAction>
    ) {
        when(action){
            is SignUpAction.SignUpButtonClicked -> {
                if (currentState.name.isEmpty()){
                    store.dispatch(SignUpAction.InvalidNameSubmitted)
                    return
                }
                if (isEmailInvalid(currentState)){
                    store.dispatch(SignUpAction.InvalidEmailSubmitted)
                    return
                }
                if (arePasswordsNotTheSame(currentState)){
                    store.dispatch(SignUpAction.InvalidPasswordSubmitted(SIGN_UP_ERROR_PASSWORDS_NOT_MATCH))
                    return
                }

                if (isPasswordInsecure(currentState)){
                    store.dispatch(SignUpAction.InvalidPasswordSubmitted(SIGN_UP_ERROR_PASSWORD_INSECURE))
                    return
                }

                signUpUser(store, currentState)
            }
        }
    }

    private suspend fun signUpUser(
        store: Store<SignUpViewState, SignUpAction>,
        currentState: SignUpViewState
    ) {
        store.dispatch(SignUpAction.SignUpStarted)

        val signUpResponse = loginRepository.signUp(
            name = currentState.name,
            email = currentState.email,
            password = currentState.password
        )

        if (signUpResponse.isSuccessful){

            val saveUserResponse = loginRepository.saveUser(
                user = getUser(signUpResponse, currentState)
            )

            if (saveUserResponse.isSuccessful){
                store.dispatch(SignUpAction.SignUpCompleted(getUser(signUpResponse, currentState)))
            } else {
                store.dispatch(SignUpAction.SignUpFailed(signUpResponse.error))
            }
        } else {
            store.dispatch(SignUpAction.SignUpFailed(signUpResponse.error))
        }
    }

    private fun getUser(response: SignUpResponse, currentState: SignUpViewState): User{
        return User(
            id = response.userSignUp.id,
            name = response.userSignUp.name,
            email = response.userSignUp.email,
            type = getUserType(currentState)
        )
    }

    private fun getUserType(currentState: SignUpViewState): String {
        return when(currentState.isUserOwner){
            true -> OWNER_USER
            false -> WALKER_USER
        }
    }

    private fun isEmailInvalid(currentState: SignUpViewState): Boolean {
       return !(EMAIL_ADDRESS_PATTERN.matcher(currentState.email).matches())
    }

    private fun arePasswordsNotTheSame(currentState: SignUpViewState): Boolean {
        return currentState.password != currentState.confPassword
    }

    private fun isPasswordInsecure(currentState: SignUpViewState): Boolean {
        return currentState.password.length < 6
    }

}