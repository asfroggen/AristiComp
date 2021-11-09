package com.esaudev.aristicomp.di

import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.repository.AuthRepositoryFirebaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.esaudev.aristicomp.di.FirebaseModule.UsersCollection
import com.esaudev.aristicomp.di.FirebaseModule.PetsCollection
import com.esaudev.aristicomp.owner.repository.pets.OwnerPetsRepository
import com.esaudev.aristicomp.owner.repository.pets.OwnerPetsRepositoryImpl
import com.esaudev.aristicomp.owner.repository.walks.OwnerWalksRepository
import com.esaudev.aristicomp.owner.repository.walks.OwnerWalksRepositoryImpl
import com.esaudev.aristicomp.walker.repository.WalkerWalksRepository
import com.esaudev.aristicomp.walker.repository.WalkerWalksRepositoryImpl

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

    @Singleton
    @Provides
    fun provideOwnerPetsRepository(
        @PetsCollection petsCollection: CollectionReference
    ): OwnerPetsRepository {
        return OwnerPetsRepositoryImpl(
            petsCollection
        )
    }

    @Singleton
    @Provides
    fun provideOwnerWalksRepository(
        @FirebaseModule.WalksCollection walksCollection: CollectionReference,
        firebaseAuth: FirebaseAuth
    ): OwnerWalksRepository {
        return OwnerWalksRepositoryImpl(
            walksCollection,
            firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideWalkerWalksRepository(
        @FirebaseModule.WalksCollection walksCollection: CollectionReference,
        firebaseAuth: FirebaseAuth
    ): WalkerWalksRepository {
        return WalkerWalksRepositoryImpl(
            walksCollection,
            firebaseAuth
        )
    }
}