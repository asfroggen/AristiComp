package com.esaudev.aristicomp.model

import android.os.Parcelable
import com.esaudev.aristicomp.utils.Constants.TO_BE_DEFINED
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Walk (
    val id: String = UUID.randomUUID().toString(),
    val petID: String = "",
    val ownerID: String = "",
    val status: String = "",
    val walkerID: String = TO_BE_DEFINED,
    val date: String = "",
    val time: String = "",
    val walkDuration: String = "1 h",
    val ownerName: String = "",
    val walkerName: String = TO_BE_DEFINED,
    val comments: String = "",
    val petImage: String = "",
    val petName: String = "",
    val petRace: String = "",
    val fullDate: String = ""
        ): Parcelable