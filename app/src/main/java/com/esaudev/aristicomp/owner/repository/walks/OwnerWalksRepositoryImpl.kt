package com.esaudev.aristicomp.owner.repository.walks

import android.util.Log
import com.esaudev.aristicomp.di.FirebaseModule
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.OWNER_ID_LABEL
import com.esaudev.aristicomp.utils.Constants.STATUS_LABEL
import com.esaudev.aristicomp.utils.DataState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OwnerWalksRepositoryImpl @Inject constructor(
    @FirebaseModule.WalksCollection private val walksCollection: CollectionReference
): OwnerWalksRepository {
    override suspend fun saveWalk(walk: Walk): Flow<DataState<Boolean>> = flow {
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
                emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override suspend fun getWalksBTypeAndOwner(type: String, ownerID: String): Flow<DataState<List<Walk>>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessFul = false
            val walks = walksCollection
                .whereEqualTo(OWNER_ID_LABEL, ownerID)
                .whereEqualTo(STATUS_LABEL, type)
                .get()
                .await()
                .toObjects(Walk::class.java)

            isSuccessFul = walks.size > 0

            if (isSuccessFul){
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


}