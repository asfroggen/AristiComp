package com.esaudev.aristicomp.auth.ui.login

object LoginConstants {

    /*
    Data Constants
     */
    const val USER_NOT_LOGGED = "user_not_signed"

    /*
    Login error messages provided by Firebase Auth
     */
    const val LOGIN_ERROR_USER_NOT_EXISTS : String = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val LOGIN_ERROR_WRONG_PASSWORD : String = "The password is invalid or the user does not have a password."
    const val LOGIN_ERROR_UNKNOWN : String = "Unknown"
    const val LOGIN_ERROR_EMAIL_EMPTY = "email_empty"
    const val LOGIN_ERROR_PASSWORD_EMPTY = "password_empty"

    /*
    Sign up messages provided by Firebase Auth
     */
    const val SIGN_UP_ERROR_USER_ALREADY_EXISTS : String = "The email address is already in use by another account."
    const val SIGN_UP_ERROR_NAME_EMPTY = "name_empty"
    const val SIGN_UP_ERROR_EMAIL_EMPTY = "email_empty"
    const val SIGN_UP_ERROR_PASSWORD_EMPTY = "password_empty"
    const val SIGN_UP_ERROR_CONF_PASSWORD_EMPTY = "password_empty"
    /*
    Collection IDs for Firebase Firestore
    */
    const val USERS_COLLECTION = "users"
}