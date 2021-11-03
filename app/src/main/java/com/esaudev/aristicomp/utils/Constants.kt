package com.esaudev.aristicomp.utils

import com.google.firebase.auth.FirebaseUser

object Constants {

    // General
    const val VALUE_REQUIRED = "value_required"

    // Bundles
    const val USER_BUNDLE = "user_bundle"
    const val USER_PASSWORD_BUNDLE = "user_password_bundle"

    // Firebase
    var FIREBASE_USER_SIGNED: FirebaseUser? = null

    // Shared preferences
    const val ENCRYPTED_SHARED_PREFERENCES_NAME = "ENCRYPTED_SHARED_PREFERENCES_NAME"
    const val SHARED_EMAIL = "email"
    const val SHARED_PASSWORD = "password"
}