package com.kofar.appuk.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataArtikel(
    var id: Int = 0,
    var judul_artikel: String? = null,
    var penulis_artikel: String? = null,
    var gambar_artikel: String? = null,
    var tanggal_publish: String? = null,
    var paragraf_satu: String? = null,
    var paragraf_dua: String? = null,
    var paragraf_tiga: String? = null,
    var paragraf_empat: String? = null,
    var paragraf_lima: String? = null

) : Parcelable {

}