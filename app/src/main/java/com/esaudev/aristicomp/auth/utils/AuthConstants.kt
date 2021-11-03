package com.esaudev.aristicomp.auth.utils

import java.util.regex.Pattern

object AuthConstants {

    /*
    Data Constants
     */
    const val USER_NOT_LOGGED = "user_not_signed"
    const val INFO_NOT_SET = "info_not_set"

    const val OWNER_USER = "owner"
    const val WALKER_USER = "walker"

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
    const val SIGN_UP_ERROR_UNKNOWN: String = "Unknown"
    const val SIGN_UP_ERROR_NAME_EMPTY = "name_empty"
    const val SIGN_UP_ERROR_EMAIL_EMPTY = "email_empty"
    const val SIGN_UP_ERROR_PASSWORD_EMPTY = "password_empty"
    const val SIGN_UP_ERROR_CONF_PASSWORD_EMPTY = "password_empty"
    const val SIGN_UP_ERROR_EMAIL_INVALID = "email_invalid"
    const val SIGN_UP_ERROR_PASSWORDS_NOT_MATCH = "password_not_match"
    const val SIGN_UP_ERROR_PASSWORD_INSECURE = "password_insecure"

    /*
    Forgot password error messages
     */
    const val FORGOT_ERROR_BAD_EMAIL = "The email address is badly formatted."
    const val FORGOT_ERROR_EMAIL_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val FORGOT_ERROR_EMAIL_EMPTY = "email_empty"
    const val FORGOT_ERROR_GENERAL = "email_not_found"

    /*
    Collection IDs for Firebase Firestore
    */
    const val USERS_COLLECTION = "users"

    /*
    Patterns
     */
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

}