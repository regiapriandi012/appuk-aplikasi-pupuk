package com.kofar.appuk.db

import android.provider.BaseColumns

internal class DatabaseArtikelContract {
    internal class QuoteColumns : BaseColumns {
        companion object {
            const val TABLE_ARTIKEL = "data_artikel"
            const val _ID = "_id"
            const val penulis_artikel = "penulis_artikel"
            const val judul_artikel = "judul_artikel"
            const val gambar_artikel = "gambar_artikel"
            const val tanggal_publish = "tanggal_publish"
            const val paragraf_satu = "paragraf_satu"
            const val paragraf_dua = "paragraf_dua"
            const val paragraf_tiga = "paragraf_tiga"
            const val paragraf_empat = "paragraf_empat"
            const val paragraf_lima = "paragraf_lima"
        }
    }
}
