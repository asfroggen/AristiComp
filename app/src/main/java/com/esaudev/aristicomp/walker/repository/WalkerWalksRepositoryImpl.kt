package com.esaudev.aristicomp.walker.repository

import com.esaudev.aristicomp.di.FirebaseModule
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.STATUS_LABEL
import com.esaudev.aristicomp.utils.DataState
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalkerWalksRepositoryImpl @Inject constructor(
    @FirebaseModule.WalksCollection private val walksCollection: CollectionReference
): WalkerWalksRepository {
    override suspend fun getWalksAvailable(): Flow<DataState<List<Walk>>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessful = false
            val walks = walksCollection
                .whereEqualTo(STATUS_LABEL, WalkStatus.PENDING.toString())
                .get()
                .await()
                .toObjects(Walk::class.java)

            isSuccessful = walks.size > 0

            if (isSuccessful) {
                emit(DataState.Success(walks))
            } else {
                emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            }
        } catch (e:Exception) {

            emit(DataState.Error(Constants.NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }
}