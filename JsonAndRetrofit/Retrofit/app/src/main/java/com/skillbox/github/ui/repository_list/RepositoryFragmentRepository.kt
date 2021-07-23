package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Network
import com.skillbox.github.data.ServerItemsWrapper
import com.skillbox.github.data.UsersRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryFragmentRepository {
    fun getUsersInfo(
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
                        val responseString = response.body().toString()
                        Log.e("server", responseString)
                        try {
                            val adapter = createMoshiAndAdapter()
                            try {
                                val movies = adapter.fromJson(responseString)
                                if (movies != null) {
                                    onComplete(listOf(movies))
                                } else {
                                    onError("Фильмы не найдены")
                                    onComplete(emptyList())
                                }
                            } catch (e: Exception) {
                                Log.e("server", "${e.message}")
                                onError("ошибка ${e.message}")
                                onComplete(emptyList())
                            }
                        } catch (e: Exception) {
                            Log.e("server", "${e.message}")
                            onError("${e.message}")
                            onComplete(emptyList())
                        }
                    } else {
                        onError("incorrect status code")
                        onComplete(emptyList())
                    }
                }
            }
        )
    }

    //создание адаптера
    private fun createMoshiAndAdapter(): JsonAdapter<UsersRepository> {
        //объект типа Moshi
        val moshi = Moshi.Builder()
            .build()
        return moshi.adapter(UsersRepository::class.java).nonNull()
    }
}