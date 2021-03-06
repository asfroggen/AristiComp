package com.esaudev.aristicomp.walker.repository

import com.esaudev.aristicomp.di.FirebaseModule
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.NETWORK_UNKNOWN_ERROR
import com.esaudev.aristicomp.utils.Constants.STATUS_LABEL
import com.esaudev.aristicomp.utils.Constants.WALKER_ID_LABEL
import com.esaudev.aristicomp.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalkerWalksRepositoryImpl @Inject constructor(
    @FirebaseModule.WalksCollection private val walksCollection: CollectionReference,
    private val firebaseAuth: FirebaseAuth
): WalkerWalksRepository {
    override suspend fun getWalksAvailable(): Flow<DataState<List<Walk>>> = callbackFlow {

        val eventDocument = walksCollection
            .whereEqualTo(STATUS_LABEL, WalkStatus.PENDING.toString())

        val subscription = eventDocument.addSnapshotListener { snapshot, error ->

            if (error != null) {
                this.trySend(DataState.Error(NETWORK_UNKNOWN_ERROR)).isSuccess
                return@addSnapshotListener
            }

            if (snapshot != null){

                val newState = snapshot.documents

                if (newState.isEmpty()) {
                    this.trySend(DataState.Error(NETWORK_UNKNOWN_ERROR)).isSuccess
                    return@addSnapshotListener
                }

                val walks = snapshot.toObjects(Walk::class.java)

                if (walks.isEmpty()) {
                    this.trySend(DataState.Error(NETWORK_UNKNOWN_ERROR)).isSuccess
                    return@addSnapshotListener
                }

                this.trySend(DataState.Success(walks)).isSuccess
            }
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun getWalksByTypeAndWalker(type: String, walkerID: String): Flow<DataState<List<Walk>>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessful = false
            val walks = walksCollection
                .whereEqualTo(WALKER_ID_LABEL, walkerID)
                .whereEqualTo(STATUS_LABEL, type)
                .get()
                .await()
                .toObjects(Walk::class.java)

            isSuccessful = walks.size > 0

            if (isSuccessful){
                emit(DataState.Success(walks))
            } else {
                emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception) {

            emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override suspend fun acceptWalk(walk: Walk): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessful = false
            walksCollection
                .document(walk.id)
                .set(walk, SetOptions.merge())
                .addOnSuccessListener { isSuccessful = true }
                .addOnFailureListener { isSuccessful = false }
                .await()

            if (isSuccessful){
                emit(DataState.Success(isSuccessful))
            } else {
                emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override suspend fun finishWalk(walk: Walk): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessful = false
            walksCollection
                .document(walk.id)
                .set(walk, SetOptions.merge())
                .addOnSuccessListener { isSuccessful = true }
                .addOnFailureListener { isSuccessful = false }
                .await()

            if (isSuccessful){
                emit(DataState.Success(isSuccessful))
            } else {
                emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override suspend fun logOut(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            firebaseAuth.signOut()
            emit(DataState.Success(true))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }
}