package com.esaudev.aristicomp.auth.di

import com.esaudev.aristicomp.auth.utils.AuthConstants.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideAuthInstance(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @UsersCollection
    @Singleton
    @Provides
    fun provideUsersCollection(
        firestore: FirebaseFirestore
    ): CollectionReference {
        return firestore.collection(USERS_COLLECTION)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UsersCollection
}