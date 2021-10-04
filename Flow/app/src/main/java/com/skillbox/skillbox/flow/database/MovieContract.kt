package com.skillbox.skillbox.flow.database

object MovieContract {
    const val TABLE_NAME = "movies"

    object Columns {
        const val ID = "id"
        const val TITLE = "title"
        const val YEAR = "year"
        const val GENRE = "genre"
        const val RUNTIME = "runtime"
        const val POSTER = "poster"
        const val TYPE = "type"
    }
}