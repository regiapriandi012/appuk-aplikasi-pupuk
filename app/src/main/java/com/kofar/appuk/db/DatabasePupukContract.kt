package com.kofar.appuk.db

import android.provider.BaseColumns

class DatabasePupukContract {
    internal class QuoteColumns : BaseColumns {
        companion object {
            const val TABLE_PUPUK = "data_pupuk"
            const val _ID = "_id"
            const val nama_pupuk = "nama_pupuk"
            const val harga_pupuk = "harga_pupuk"
            const val gambar_pupuk = "gambar_pupuk"
            const val keterangan_pupuk = "gambar_pupuk"
        }
    }
}