package com.skillbox.skillbox.flow.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.skillbox.flow.classes.MovieType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = MovieContract.TABLE_NAME)
@TypeConverters(MovieTypesConverter::class)
@JsonClass(generateAdapter = true)
@Parcelize
data class MovieEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = MovieContract.Columns.ID)
        val id: Int = 0,
        @ColumnInfo(name = MovieContract.Columns.TITLE)
        @Json(name = "Title")
        val title: String,
        @ColumnInfo(name = MovieContract.Columns.YEAR)
        @Json(name = "Year")
        val year: String,
        @ColumnInfo(name = MovieContract.Columns.RUNTIME)
        @Json(name = "Runtime")
        val runtime: String,
        @ColumnInfo(name = MovieContract.Columns.GENRE)
        @Json(name = "Genre")
        val genre: String,
        @ColumnInfo(name = MovieContract.Columns.POSTER)
        @Json(name = "Poster")
        val poster: String?,
        @ColumnInfo(name = MovieContract.Columns.TYPE)
        @Json(name = "Type")
        val type: MovieType
) : Parcelable