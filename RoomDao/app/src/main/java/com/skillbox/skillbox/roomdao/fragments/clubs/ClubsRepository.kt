package com.skillbox.skillbox.roomdao.fragments.clubs

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Clubs

class ClubsRepository {
    private val clubDao = Database.instance.clubsDao()

    suspend fun saveNewClub(club: Clubs) {
        clubDao.addNewClub(listOf(club))
    }

    suspend fun deleteAllClubs() {
        clubDao.deleteAllClubs()
    }

    suspend fun getAllClubs(): List<Clubs> {
        return clubDao.getAllClubs()
    }

}