package com.kofar.appuk.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kofar.appuk.db.DatabaseArtikelContract.QuoteColumns.Companion.TABLE_ARTIKEL

internal class DatabaseArtikelHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "database_artikel"
        private const val DATABASE_VERSION = 5
        private const val SQL_CREATE_TABLE_QUOTE = "CREATE TABLE $TABLE_ARTIKEL" +
                " (${DatabaseArtikelContract.QuoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseArtikelContract.QuoteColumns.judul_artikel} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.penulis_artikel} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.gambar_artikel} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.tanggal_publish} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.paragraf_satu} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.paragraf_dua} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.paragraf_tiga} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.paragraf_empat} TEXT NOT NULL," +
                " ${DatabaseArtikelContract.QuoteColumns.paragraf_lima} TEXT NOT NULL)"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_QUOTE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ARTIKEL")
        onCreate(db)
    }
}
