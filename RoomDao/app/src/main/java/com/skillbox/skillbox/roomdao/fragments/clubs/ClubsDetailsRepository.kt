package com.skillbox.skillbox.roomdao.fragments.clubs

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class ClubsDetailsRepository {

    //    создаем инстансы необходимых Дао
    private val clubsDao = Database.instance.clubsDao()
    private val stadiumDao = Database.instance.stadiumsDao()

    //    удаление клуба
    suspend fun deleteClub(club: Clubs): Boolean {
        return try {
            clubsDao.deleteClub(club)
            true
        } catch (t: Throwable) {
            false
        }
    }

    //    обновление клуба
    suspend fun updateClub(club: Clubs) {
        clubsDao.updateClub(club)
    }

    //    получение стадиона по id
    suspend fun getStadiumById(stadiumId: Long): Stadiums {
        return stadiumDao.getStadiumById(stadiumId)
    }

    //    получение стадиона по названию
    suspend fun getStadiumByName(stadiumName: String): Stadiums {
        return stadiumDao.getStadiumByName(stadiumName)
    }

    //    получение всех стадионов
    suspend fun getAllStadiums(): List<Stadiums> {
        return stadiumDao.getAllStadiums()
    }

    //    добавление нового стадиона
    suspend fun addNewStadium(stadium: List<Stadiums>) {
        stadiumDao.addNewStadium(stadium)
    }
}