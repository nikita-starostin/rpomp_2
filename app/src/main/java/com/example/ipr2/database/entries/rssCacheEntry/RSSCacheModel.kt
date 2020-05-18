package com.example.ipr2.database.entries.rssCacheEntry

import android.content.ContentValues
import com.example.ipr2.database.Contract

class RSSCacheModel(var id: Int, var cache: String) {
    fun getContentValues(): ContentValues =
        ContentValues().apply {
            put(Contract.RSSCache.COLUMN_NAME_CACHE, cache)
        }
}