package com.skillbox.skillbox.myapplication

import com.skillbox.skillbox.myapplication.classes.Resorts
import kotlin.random.Random

class ResortRepository {

    //  добавление элемента
    fun addResort(
        resortsType: String,
        name: String,
        country: String,
        photo: String,
        place: String
    ): Resorts {
        return when (resortsType) {
            "Mountain" -> Resorts.Mountain(
                Random.nextLong(),
                name,
                country,
                photo,
                place
            )
            "Sea" -> Resorts.Sea(
                Random.nextLong(),
                name,
                country,
                photo,
                place
            )
            "Ocean" -> Resorts.Ocean(
                Random.nextLong(),
                name,
                country,
                photo,
                place
            )
            else -> error("Incorrect type of resort")
        }
    }

    //  удаление элемента
    fun deleteResort(resortsList: List<Resorts>, position: Int): List<Resorts> {
        return resortsList.filterIndexed { index, _ -> index != position } as ArrayList<Resorts>
    }
}



