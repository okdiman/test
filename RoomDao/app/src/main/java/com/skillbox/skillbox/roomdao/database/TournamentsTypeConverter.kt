package com.skillbox.skillbox.roomdao.database

import androidx.room.TypeConverter


class TournamentsTypeConverter {

    @TypeConverter
    fun convertTypeToString(type: TypeOfTournament): String = type.name

    @TypeConverter
    fun convertStringToType(typeString: String): TypeOfTournament =
        TypeOfTournament.valueOf(typeString)
}