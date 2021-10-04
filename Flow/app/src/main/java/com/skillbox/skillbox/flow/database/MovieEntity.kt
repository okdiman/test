package com.skillbox.skillbox.flow.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.skillbox.flow.classes.MovieType
import kotlinx.android.parcel.Parcelize

@Entity(tableName = MovieContract.TABLE_NAME)
@TypeConverters(MovieTypesConverter::class)
@Parcelize
data class MovieEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = MovieContract.Columns.ID)
        val id: Int,
        @ColumnInfo(name = MovieContract.Columns.TITLE)
        val title: String,
        @ColumnInfo(name = MovieContract.Columns.YEAR)
        val year: String,
        @ColumnInfo(name = MovieContract.Columns.RUNTIME)
        val runtime: String,
        @ColumnInfo(name = MovieContract.Columns.GENRE)
        val genre: String,
        @ColumnInfo(name = MovieContract.Columns.POSTER)
        val poster: String?,
        @ColumnInfo(name = MovieContract.Columns.TYPE)
        val type: MovieType
) : Parcelable