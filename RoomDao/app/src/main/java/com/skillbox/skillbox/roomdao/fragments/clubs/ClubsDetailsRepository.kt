package com.skillbox.skillbox.roomdao.fragments.clubs

import com.skillbox.skillbox.roomdao.database.Database
import com.skillbox.skillbox.roomdao.database.entities.Clubs

class ClubsDetailsRepository {

    private val clubsDao = Database.instance.clubsDao()

    suspend fun deleteClub(club: Clubs): Boolean {
        return try {
            clubsDao.deleteClub(club)
            true
        } catch (t: Throwable) {
            false
        }
    }
}