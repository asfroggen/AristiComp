package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.LoginResponse
import com.esaudev.aristicomp.auth.ui.login.actions.LoginReducer
import kotlinx.coroutines.delay

class ProdLoginService : AuthRepository {

    override suspend fun login(email: String, password: String): LoginResponse {
        delay(2000)

        return LoginResponse(
            isSuccessful = true,
            uid = "1234"
        )
    }
}
