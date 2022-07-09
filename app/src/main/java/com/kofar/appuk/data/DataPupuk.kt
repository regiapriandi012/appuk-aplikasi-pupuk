package com.kofar.appuk.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPupuk(
    var id: String? = null,
    var nama_pupuk: String? = null,
    var harga_pupuk: String? = null,
    var gambar_pupuk: String? = null,
    var keterangan_pupuk: String? = null

) : Parcelable {

}