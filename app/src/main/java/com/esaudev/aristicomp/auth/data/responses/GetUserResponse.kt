package com.esaudev.aristicomp.auth.data.responses

import com.esaudev.aristicomp.auth.models.User

data class GetUserResponse (
    val isSuccessful: Boolean = false,
    val user: User = User(),
    val error: String = ""
        )