package com.example.ipr2.database.entries.rssCacheEntry

import android.database.sqlite.SQLiteOpenHelper
import com.example.ipr2.database.Contract
import com.example.ipr2.models.RSSObject
import com.google.gson.Gson

object RSSCacheQueries {
    fun getCache(databaseOpenHelper: SQLiteOpenHelper): RSSObject? {
        val cursor = databaseOpenHelper.readableDatabase
            .query(
                Contract.RSSCache.TABLE_NAME,
                arrayOf(Contract.RSSCache.COLUMN_NAME_CACHE),
                null,
                null,
                null,
                null,
                null,
                "1"
            )
        with(cursor) {
            return if (moveToFirst()) {
                Gson().fromJson(
                    getString(getColumnIndex(Contract.RSSCache.COLUMN_NAME_CACHE)),
                    RSSObject::class.java
                )
            } else {
                null
            }
        }
    }
}