package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.LoginResponse
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.LOGIN_ERROR_UNKNOWN
import kotlinx.coroutines.delay

class AuthRepositoryFirebaseImpl : AuthRepository{
    override suspend fun login(email: String, password: String): LoginResponse {
        delay(2000)

        return LoginResponse(
            isSuccessful = false,
            error = LOGIN_ERROR_UNKNOWN
        )
    }
}