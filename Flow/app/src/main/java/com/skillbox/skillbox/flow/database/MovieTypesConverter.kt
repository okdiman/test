package com.skillbox.skillbox.flow.database

import androidx.room.TypeConverter
import com.skillbox.skillbox.flow.classes.MovieType

class MovieTypesConverter {
    @TypeConverter
    fun convertTypeToString(type: MovieType): String = type.name
    @TypeConverter
    fun convertStringToType(typeString: String): MovieType =
        MovieType.valueOf(typeString)
}