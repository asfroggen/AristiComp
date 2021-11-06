package com.esaudev.aristicomp.model

import android.os.Parcelable
import com.esaudev.aristicomp.utils.Constants.DEFAULT_DOG_IMAGE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Pet(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val race: String = "",
    val image: String = DEFAULT_DOG_IMAGE,
    val ownerID: String = ""
): Parcelable
