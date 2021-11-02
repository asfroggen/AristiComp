package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.LoginResponse
import com.esaudev.aristicomp.auth.data.responses.SaveUserResponse
import com.esaudev.aristicomp.auth.data.responses.SignUpResponse
import com.esaudev.aristicomp.auth.models.User

interface AuthRepository {

    suspend fun login(email: String, password: String): LoginResponse

    suspend fun signUp(name: String, email: String, password: String): SignUpResponse

    suspend fun saveUser(user: User): SaveUserResponse
}