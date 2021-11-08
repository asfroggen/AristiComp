package com.esaudev.aristicomp.owner.repository.walks

import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.utils.DataState
import kotlinx.coroutines.flow.Flow

interface OwnerWalksRepository {

    suspend fun saveWalk(walk: Walk): Flow<DataState<Boolean>>

    suspend fun getWalksBTypeAndOwner(type: String, ownerID: String): Flow<DataState<List<Walk>>>

    suspend fun deleteWalk(walk: Walk): Flow<DataState<Boolean>>
}