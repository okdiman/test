package com.skillbox.skillbox.roomdao.database

import androidx.room.TypeConverter


class TournamentsTypeConverter {

    //    создаем конвертер из enum'а в строку
    @TypeConverter
    fun convertTypeToString(type: TypeOfTournament): String = type.name

    //    создаем конвертер из строки в enum
    @TypeConverter
    fun convertStringToType(typeString: String): TypeOfTournament =
        TypeOfTournament.valueOf(typeString)
}