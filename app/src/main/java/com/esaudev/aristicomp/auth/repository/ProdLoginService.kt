package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.GetUserResponse
import com.esaudev.aristicomp.auth.data.responses.LoginResponse
import com.esaudev.aristicomp.auth.data.responses.SaveUserResponse
import com.esaudev.aristicomp.auth.data.responses.SignUpResponse
import com.esaudev.aristicomp.auth.models.User
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

    override suspend fun getUserData(): GetUserResponse {
        return GetUserResponse()
    }

    override suspend fun signUp(name: String, email: String, password: String): SignUpResponse {
        delay(2000)

        return SignUpResponse()
    }

    override suspend fun saveUser(user: User): SaveUserResponse {
        delay(2000)

        return SaveUserResponse()
    }
}
