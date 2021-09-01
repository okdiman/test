package com.skillbox.skillbox.roomdao.fragments.clubs

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Clubs

class ClubsRepository {

//    получаем инстанс clubsDao
    private val clubDao = Database.instance.clubsDao()

//    сохраняем новый клуб
    suspend fun saveNewClub(club: Clubs) {
        clubDao.addNewClub(listOf(club))
    }

//    удаляем все клубы
    suspend fun deleteAllClubs() {
        clubDao.deleteAllClubs()
    }

//    получаем все клубы
    suspend fun getAllClubs(): List<Clubs> {
        return clubDao.getAllClubs()
    }

}