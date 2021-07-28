package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Network
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
            object : Callback<List<UsersRepository>> {
                override fun onFailure(
                    call: Call<List<UsersRepository>>,
                    t: Throwable
                ) {
                    Log.e("server", "$t")
                    onError(t.toString())
                }

                override fun onResponse(
                    call: Call<List<UsersRepository>>,
                    response: Response<List<UsersRepository>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()!!)
                    } else {
                        onError("incorrect status code")
                        onComplete(emptyList())
                    }
                }
            }
        )
    }

    fun getStarredRepo(
        onError: (String) -> Unit,
        onComplete: (List<UsersRepository>) -> Unit
    ) {
        return Network.githubApi.getStarredRepositories().enqueue(
            object : Callback<List<UsersRepository>> {
                override fun onFailure(
                    call: Call<List<UsersRepository>>,
                    t: Throwable
                ) {
                    Log.e("server", "$t")
                    onError(t.toString())
                }

                override fun onResponse(
                    call: Call<List<UsersRepository>>,
                    response: Response<List<UsersRepository>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()!!)
                    } else {
                        onError("incorrect status code")
                        onComplete(emptyList())
                    }
                }
            }
        )
    }
}