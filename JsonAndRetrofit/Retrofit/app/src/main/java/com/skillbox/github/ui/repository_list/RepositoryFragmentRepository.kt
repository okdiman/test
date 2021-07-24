package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Network
import com.skillbox.github.data.ServerItemsWrapper
import com.skillbox.github.data.UsersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryFragmentRepository {
    fun getUsersRepoInfo(
        onError: (String) -> Unit,
        onComplete: (List<UsersRepository>) -> Unit
    ) {
        return Network.githubApi.searchUsersRepositories().enqueue(
            object : Callback<ServerItemsWrapper<UsersRepository>> {
                override fun onFailure(
                    call: Call<ServerItemsWrapper<UsersRepository>>,
                    t: Throwable
                ) {
                    Log.e("server", "$t")
                    onError(t.toString())
                }

                override fun onResponse(
                    call: Call<ServerItemsWrapper<UsersRepository>>,
                    response: Response<ServerItemsWrapper<UsersRepository>>
                ) {
                    if (response.isSuccessful) {
                      onComplete(response.body()?.items.orEmpty())
                    } else {
                        onError("incorrect status code")
                        onComplete(emptyList())
                    }
                }
            }
        )
    }
}