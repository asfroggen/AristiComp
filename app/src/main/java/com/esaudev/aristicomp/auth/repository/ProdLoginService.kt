package com.esaudev.aristicomp.auth.repository

import kotlinx.coroutines.delay

class ProdLoginService : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        delay(2000)

        return true
    }
}
