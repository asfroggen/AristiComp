package com.esaudev.aristicomp.auth.ui.login

object LoginConstants {

    val LOGIN_ERROR_EMAIL_EMPTY = "email_empty"
    val LOGIN_ERROR_PASSWORD_EMPTY = "password_empty"

    /*
    Login error messages provided by Firebase Auth
     */
    const val LOGIN_ERROR_USER_NOT_EXISTS : String = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val LOGIN_ERROR_WRONG_PASSWORD : String = "The password is invalid or the user does not have a password."
    const val LOGIN_ERROR_UNKNOWN : String = "Unknown"
}