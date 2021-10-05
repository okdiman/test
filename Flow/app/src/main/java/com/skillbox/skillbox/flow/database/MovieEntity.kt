package com.skillbox.skillbox.flow.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.skillbox.skillbox.flow.classes.MovieType
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Entity(tableName = MovieContract.TABLE_NAME)
@TypeConverters(MovieTypesConverter::class)
@JsonClass(generateAdapter = true)
@Parcelize
data class MovieEntity(
        @PrimaryKey
        @SerializedName("imdbID")
        @ColumnInfo(name = MovieContract.Columns.ID)
        val id: String,
        @ColumnInfo(name = MovieContract.Columns.TITLE)
        @SerializedName("Title")
        val title: String,
        @ColumnInfo(name = MovieContract.Columns.YEAR)
        @SerializedName("Year")
        val year: String,
        @ColumnInfo(name = MovieContract.Columns.POSTER)
        @SerializedName("Poster")
        val poster: String?,
        @ColumnInfo(name = MovieContract.Columns.TYPE)
        @SerializedName("Type")
        val type: MovieType?
) : Parcelable