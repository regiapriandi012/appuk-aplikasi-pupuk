package com.kofar.appuk.data_user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    var name: String? = null,
    var username: String? = null,
    var password: String? = null,
): Parcelable