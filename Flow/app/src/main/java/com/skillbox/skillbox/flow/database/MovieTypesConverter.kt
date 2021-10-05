package com.skillbox.skillbox.flow.database

import androidx.room.TypeConverter
import com.skillbox.skillbox.flow.classes.MovieType

//конвертер для преобразования объектов enum класса в строку для хранения в БД
class MovieTypesConverter {
    @TypeConverter
    fun convertTypeToString(type: MovieType): String = type.name
    @TypeConverter
    fun convertStringToType(typeString: String): MovieType =
        MovieType.valueOf(typeString)
}