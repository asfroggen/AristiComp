package com.esaudev.aristicomp.auth.models

import android.os.Parcelable
import com.esaudev.aristicomp.auth.utils.AuthConstants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (

    var id: String = AuthConstants.INFO_NOT_SET,
    val name: String = AuthConstants.INFO_NOT_SET,
    val email: String = AuthConstants.INFO_NOT_SET,
    val type: String = AuthConstants.INFO_NOT_SET

) : Parcelable