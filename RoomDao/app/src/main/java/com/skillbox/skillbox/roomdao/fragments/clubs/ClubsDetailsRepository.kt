package com.skillbox.skillbox.roomdao.fragments.clubs

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums

class ClubsDetailsRepository {

    private val clubsDao = Database.instance.clubsDao()
    private val stadiumDao = Database.instance.stadiumsDao()

    suspend fun deleteClub(club: Clubs): Boolean {
        return try {
            clubsDao.deleteClub(club)
            true
        } catch (t: Throwable) {
            false
        }
    }

    suspend fun updateClub(club: Clubs): Boolean {
        return try {
            clubsDao.updateClub(club)
            true
        } catch (t: Throwable) {
            false
        }
    }

    suspend fun getStadiumById(stadiumId: Long): Stadiums {
        return stadiumDao.getStadiumById(stadiumId)
    }

    suspend fun getStadiumByName(stadiumName: String): Stadiums {
        return stadiumDao.getStadiumByName(stadiumName)
    }

    suspend fun getAllStadiums(): List<Stadiums> {
        return stadiumDao.getAllStadiums()
    }

    suspend fun addNewStadium(stadium: List<Stadiums>): Boolean {
        return try {
            stadiumDao.addNewStadium(stadium)
            true
        } catch (t: Throwable) {
            false
        }
    }
}