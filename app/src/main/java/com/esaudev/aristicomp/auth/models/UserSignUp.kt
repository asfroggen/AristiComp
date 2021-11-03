package com.esaudev.aristicomp.auth.models

import android.os.Parcelable
import com.esaudev.aristicomp.auth.utils.AuthConstants.INFO_NOT_SET
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignUp (

    var id: String = INFO_NOT_SET,
    val name: String = INFO_NOT_SET,
    val email: String = INFO_NOT_SET

) : Parcelable