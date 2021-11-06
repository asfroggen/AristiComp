package com.esaudev.aristicomp.owner.repository

import android.app.Activity
import android.net.Uri
import android.service.autofill.Dataset
import android.util.Log
import androidx.fragment.app.Fragment
import com.esaudev.aristicomp.di.FirebaseModule
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.owner.ui.pets.new_pet.OwnerNewPetFragment
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.NETWORK_UNKNOWN_ERROR
import com.esaudev.aristicomp.utils.Constants.OWNER_ID_LABEL
import com.esaudev.aristicomp.utils.DataState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OwnerRepositoryImpl @Inject constructor(
    @FirebaseModule.PetsCollection private val petsCollection: CollectionReference
): OwnerRepository {
    override suspend fun savePet(pet: Pet): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var isSuccessfully = false

            petsCollection
                .document(pet.id)
                .set(pet, SetOptions.merge())
                .addOnSuccessListener { isSuccessfully = true }
                .addOnFailureListener { isSuccessfully = false }
                .await()

            if (isSuccessfully){
                emit(DataState.Success(isSuccessfully))
            } else {
                emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override suspend fun getPetsByOwner(ownerID: String): Flow<DataState<List<Pet>>> = flow {
        emit(DataState.Loading)
        try {

            var isSuccessful = false
            val pets = petsCollection
                .whereEqualTo(OWNER_ID_LABEL, ownerID)
                .get()
                .await()
                .toObjects(Pet::class.java)

            isSuccessful = pets.size > 0

            if (isSuccessful){
                emit(DataState.Success(pets))
            } else {
                emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            }
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(NETWORK_UNKNOWN_ERROR))
            emit(DataState.Finished)
        }
    }

    override fun uploadPetImage(
        activity: Activity,
        imageFileURI: Uri?,
        imageType: String,
        fragment: Fragment
    ) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType+System.currentTimeMillis()+"."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )
        // Adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        when(fragment) {
                            is OwnerNewPetFragment -> fragment.uploadImageSuccess(uri.toString())
                        }
                    }
            }
            .addOnFailureListener { exception ->
                // Hide the progress dialog if there is any error. And print the error in log.
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )

                when(fragment) {
                    is OwnerNewPetFragment -> fragment.uploadImageFailure()
                }
            }
    }
}