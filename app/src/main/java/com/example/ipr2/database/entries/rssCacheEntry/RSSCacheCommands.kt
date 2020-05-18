package com.example.ipr2.database.entries.rssCacheEntry

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.ipr2.database.Contract
import com.example.ipr2.models.RSSObject
import com.google.gson.Gson

object RSSCacheCommands {
    fun updateCache(databaseOpenHelper: SQLiteOpenHelper, rssObject: RSSObject) {
        databaseOpenHelper.writableDatabase.delete(
            Contract.RSSCache.TABLE_NAME,
            null,
            null
        )
        databaseOpenHelper.readableDatabase.insert(
            Contract.RSSCache.TABLE_NAME,
            null,
            contentValuesOf(Contract.RSSCache.COLUMN_NAME_CACHE to Gson().toJson(rssObject))
        )
    }
}