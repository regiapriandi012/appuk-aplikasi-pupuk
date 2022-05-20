package com.kofar.appuk

import android.os.Parcel
import android.os.Parcelable

@MyData.Parcelize
data class MyData(
    var nama_pupuk: String?,
    var harga_pupuk: String?,
    var gambar_pupuk: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    annotation class Parcelize

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama_pupuk)
        parcel.writeString(harga_pupuk)
        parcel.writeString(gambar_pupuk)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyData> {
        override fun createFromParcel(parcel: Parcel): MyData {
            return MyData(parcel)
        }

        override fun newArray(size: Int): Array<MyData?> {
            return arrayOfNulls(size)
        }
    }
}