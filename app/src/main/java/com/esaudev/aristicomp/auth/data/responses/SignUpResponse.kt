package com.esaudev.aristicomp.auth.data.responses

import com.esaudev.aristicomp.auth.models.UserSignUp

data class SignUpResponse(
    val isSuccessful: Boolean = false,
    val userSignUp: UserSignUp = UserSignUp(),
    val error: String = ""
)
