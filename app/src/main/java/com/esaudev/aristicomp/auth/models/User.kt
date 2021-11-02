package com.esaudev.aristicomp.auth.models

import android.os.Parcelable
import com.esaudev.aristicomp.auth.ui.login.LoginConstants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (

    var id: String = LoginConstants.INFO_NOT_SET,
    val name: String = LoginConstants.INFO_NOT_SET,
    val email: String = LoginConstants.INFO_NOT_SET,
    val type: String = LoginConstants.INFO_NOT_SET

) : Parcelable