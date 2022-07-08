package com.kofar.appuk.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.kofar.appuk.db.DatabaseArtikelContract.QuoteColumns.Companion.TABLE_ARTIKEL
import com.kofar.appuk.db.DatabaseArtikelContract.QuoteColumns.Companion._ID

class ArtikelHelper(context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseArtikelHelper
        private var INSTANCE: ArtikelHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): ArtikelHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: ArtikelHelper(context)
            }
    }
    init {
        dataBaseHelper = DatabaseArtikelHelper(context)
    }
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
            TABLE_ARTIKEL,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }
    fun queryById(id: String): Cursor {
        return database.query(
            TABLE_ARTIKEL,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(
            TABLE_ARTIKEL, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            TABLE_ARTIKEL, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(
            TABLE_ARTIKEL, "$_ID = '$id'", null)
    }
}