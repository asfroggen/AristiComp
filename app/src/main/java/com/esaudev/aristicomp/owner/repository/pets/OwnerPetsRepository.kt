package com.esaudev.aristicomp.owner.repository.pets

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.utils.DataState
import kotlinx.coroutines.flow.Flow

interface OwnerPetsRepository {

    suspend fun savePet(pet: Pet): Flow<DataState<Boolean>>

    suspend fun getPetsByOwner(ownerID: String): Flow<DataState<List<Pet>>>

    suspend fun deletePet(pet: Pet): Flow<DataState<Boolean>>

    fun uploadPetImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment)

}