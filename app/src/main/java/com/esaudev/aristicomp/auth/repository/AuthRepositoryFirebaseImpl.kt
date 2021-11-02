package com.esaudev.aristicomp.auth.repository

import com.esaudev.aristicomp.auth.data.responses.LoginResponse
import com.esaudev.aristicomp.auth.data.responses.SignUpResponse
import com.esaudev.aristicomp.auth.di.FirebaseModule.UsersCollection
import com.esaudev.aristicomp.auth.models.User
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.INFO_NOT_SET
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.LOGIN_ERROR_UNKNOWN
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.SIGN_UP_ERROR_UNKNOWN
import com.esaudev.aristicomp.auth.ui.login.LoginConstants.USER_NOT_LOGGED
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryFirebaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @UsersCollection private val usersCollection: CollectionReference
): AuthRepository{

    override suspend fun login(email: String, password: String): LoginResponse {
        try {
            var loginStatus: Boolean = false
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginStatus = true
                }.addOnFailureListener {
                    loginStatus = false
                }.await()

            return LoginResponse(
                isSuccessful = loginStatus,
                uid = firebaseAuth.currentUser?.uid?: USER_NOT_LOGGED
            )
        } catch (e: Exception){
            return LoginResponse(
                isSuccessful = false,
                uid = USER_NOT_LOGGED,
                error = e.message?: LOGIN_ERROR_UNKNOWN
            )
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): SignUpResponse {
        try {
            val user = User(
                name = name,
                email = email
            )
            var signUpStatus: Boolean = false
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        firebaseUser.sendEmailVerification()

                        user.id = firebaseUser.uid

                    }
                }.await()

            signUpStatus = user.id != INFO_NOT_SET

            return SignUpResponse(
                isSuccessful = signUpStatus,
                user = user
            )

        } catch (e: Exception){
            return SignUpResponse(
                isSuccessful = false,
                user = User(),
                error = e.message?: SIGN_UP_ERROR_UNKNOWN
            )
        }
    }
}