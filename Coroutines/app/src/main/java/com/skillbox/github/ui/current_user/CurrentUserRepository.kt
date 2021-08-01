package com.skillbox.github.ui.current_user

import android.util.Log
import com.skillbox.github.data.Network
import com.skillbox.github.data.UsersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentUserRepository {

    //получение информации о пользователе
    fun getUsersInfo(
        onError: (String) -> Unit,
        onComplete: (String) -> Unit
    ) {
        //запрос на получение инфо о пользователе
        Network.githubApi.searchUsersInformation().enqueue(
            //создание объекта Callback для Retrofit
            object : Callback<UsersInfo> {
                override fun onFailure(call: Call<UsersInfo>, t: Throwable) {
                    Log.e("server", "$t")
                    onError(t.toString())
                }

                override fun onResponse(call: Call<UsersInfo>, response: Response<UsersInfo>) {
                    if (response.isSuccessful) {
                        onComplete(response.body().toString())
                    } else {
                        onError("incorrect status code")
                    }
                }
            }
        )
    }
}