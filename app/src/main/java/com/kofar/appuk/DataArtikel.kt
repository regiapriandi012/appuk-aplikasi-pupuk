package com.kofar.appuk

import android.os.Parcel
import android.os.Parcelable

@DataPupuk.Parcelize
data class DataArtikel(
    var judul_artikel: String?,
    var gambar_artikel: String?,
    var tanggal_publish: String?,
    var paragraf_satu: String?,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    annotation class Parcelize

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul_artikel)
        parcel.writeString(gambar_artikel)
        parcel.writeString(tanggal_publish)
        parcel.writeString(paragraf_satu)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataArtikel> {
        override fun createFromParcel(parcel: Parcel): DataArtikel {
            return DataArtikel(parcel)
        }

        override fun newArray(size: Int): Array<DataArtikel?> {
            return arrayOfNulls(size)
        }
    }
}