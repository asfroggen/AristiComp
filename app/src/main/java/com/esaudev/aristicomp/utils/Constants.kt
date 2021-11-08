package com.esaudev.aristicomp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser

object Constants {

    // General
    const val VALUE_REQUIRED = "value_required"
    const val TO_BE_DEFINED = "to_be_defined"

    // Bundles
    const val USER_BUNDLE = "user_bundle"
    const val USER_PASSWORD_BUNDLE = "user_password_bundle"
    const val PET_BUNDLE = "pet_bundle"

    // Firebase
    var FIREBASE_USER_SIGNED: FirebaseUser? = null
    const val DEFAULT_DOG_IMAGE = "https://firebasestorage.googleapis.com/v0/b/perritos-felices-3834c.appspot.com/o/default_dog.png?alt=media&token=4dfc7618-261e-4696-aec6-a745a69b6e38"

    // Shared preferences
    const val ENCRYPTED_SHARED_PREFERENCES_NAME = "ENCRYPTED_SHARED_PREFERENCES_NAME"
    const val SHARED_EMAIL = "email"
    const val SHARED_PASSWORD = "password"

    // Firebase querys
    const val PETS_COLLECTION = "pets"
    const val WALKS_COLLECTION = "walks"
    const val OWNER_ID_LABEL = "ownerID"
    const val STATUS_LABEL = "status"
    const val DOG_IMAGE: String = "dog_image"

    // Network
    const val NETWORK_UNKNOWN_ERROR = "network_unknown_error"

    // PERMISSIONS
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    // Utils
    fun showImageChooser(fragment: Fragment){
        // An intent for launching the image selection of phone storage.
        val gallertIntent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // Launches the image selection of phone storage using the constant code.
        fragment.startActivityForResult(gallertIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String?{
        /* MimeType: Two way that maps MIME-types to file extensions and vice versa.
        *  getSingleton(): Get the singleton instance of MimeTypeMap.
        *  getExtensionFromMimeType: Return the registered extension for the given MIME type.
        *  contentResolver.getType: Return the MIME type of the given content URL. */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}