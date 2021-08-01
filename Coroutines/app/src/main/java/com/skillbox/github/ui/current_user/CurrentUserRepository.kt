package com.skillbox.github.ui.current_user

import android.util.Log
import com.skillbox.github.data.Followings
import com.skillbox.github.data.Network
import com.skillbox.github.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentUserRepository {

    //получение информации о пользователе
    suspend fun getUsersInfo(): UsersInfo {
        //запрос на получение инфо о пользователе
        return Network.githubApi.searchUsersInformation()
    }

    suspend fun getUsersFollowing() : List<Followings>{
        return Network.githubApi.getFollowing()
    }
}