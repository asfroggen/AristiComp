package com.esaudev.aristicomp.auth.di

import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.repository.AuthRepositoryFirebaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.esaudev.aristicomp.auth.di.FirebaseModule.UsersCollection

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        authInstance: FirebaseAuth,
        @UsersCollection usersCollection: CollectionReference
    ): AuthRepository{
        return AuthRepositoryFirebaseImpl(
            authInstance,
            usersCollection
        )
    }
}