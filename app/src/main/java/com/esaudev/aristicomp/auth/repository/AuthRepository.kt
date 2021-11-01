package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.LoginResponse

interface AuthRepository {

    suspend fun login(email: String, password: String): LoginResponse
}