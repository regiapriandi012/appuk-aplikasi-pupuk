package com.kofar.appuk.data_theme

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingModel (
    var isDarkTheme: Boolean = false
): Parcelable