package com.esaudev.aristicomp.walker.repository

import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.utils.DataState
import kotlinx.coroutines.flow.Flow

interface WalkerWalksRepository {

    suspend fun getWalksAvailable(): Flow<DataState<List<Walk>>>

    suspend fun acceptWalk(walk: Walk): Flow<DataState<Boolean>>
}