package com.example.ipr2.database

object Contract {
    const val DATABASE_NAME = "ipr2RSSCache"
    const val DATABASE_VERSION = 1

    object RSSCache {
        const val TABLE_NAME = "RSSObject"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_CACHE = "cache"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_NAME_ID INTEGER PRIMARY KEY, $COLUMN_NAME_CACHE TEXT);"
        const val SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS $TABLE_NAME;"
    }
}