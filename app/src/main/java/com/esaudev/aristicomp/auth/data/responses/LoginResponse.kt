package com.esaudev.aristicomp.auth.data.responses

data class LoginResponse(
    val isSuccessful: Boolean = false,
    val uid: String = "",
    val error: String = ""
)
