package com.kofar.appuk

import android.database.Cursor
import android.icu.text.SimpleDateFormat
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.db.DatabaseArtikelContract
import java.util.*

object artikelhelper {
    const val EXTRA_ARTIKEL = "extra_artikel"
    const val EXTRA_POSITION = "extra_position"
    const val REQUEST_ADD = 100
    const val RESULT_ADD = 101
    const val REQUEST_UPDATE = 200
    const val RESULT_UPDATE = 201
    const val RESULT_DELETE = 301
    const val ALERT_DIALOG_CLOSE = 10
    const val ALERT_DIALOG_DELETE = 20
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<DataArtikel> {
        val artikelList = ArrayList<DataArtikel>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id =
                    getInt(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns._ID))
                val judul_artikel =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.judul_artikel))
                val penulis_artikel =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.penulis_artikel))
                val gambar_artikel =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.gambar_artikel))
                val tanggal_publish =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.tanggal_publish))
                val paragraf_satu =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.paragraf_satu))
                val paragraf_dua =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.paragraf_dua))
                val paragraf_tiga =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.paragraf_tiga))
                val paragraf_empat =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.paragraf_empat))
                val paragraf_lima =
                    getString(getColumnIndexOrThrow(DatabaseArtikelContract.QuoteColumns.paragraf_lima))
                artikelList.add(DataArtikel(id, judul_artikel, penulis_artikel, gambar_artikel, tanggal_publish, paragraf_satu, paragraf_dua, paragraf_tiga, paragraf_empat, paragraf_lima))
            }
        }
        return artikelList
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}