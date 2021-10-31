package com.esaudev.aristicomp.auth.repository

interface AuthRepository {

    suspend fun login(email: String, password: String): Boolean
}