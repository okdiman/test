package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoRepoRepository {
    fun checkRepoStatus(
        nameRepo: String,
        nameOwner: String,
        onError: (String) -> Unit,
        onComplete: (Int) -> Unit
    ) {
        Network.githubApi.checkIsStarredOrNot(nameOwner, nameRepo).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t.toString())
                    onComplete(0)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("server", "${response}")
                    onComplete(response.code())
                }
            }
        )

    }

    fun addStar(
        nameRepo: String,
        nameOwner: String,
        onError: (String) -> Unit,
        onComplete: (Int) -> Unit
    ) {
        Network.githubApi.addStar(nameRepo, nameOwner).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t.toString())
                    onComplete(0)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("server", "${response}")
                    onComplete(response.code())
                }
            }
        )
    }

    fun delStar(
        nameRepo: String,
        nameOwner: String,
        onError: (String) -> Unit,
        onComplete: (Int) -> Unit
    ) {
        Network.githubApi.delStar(nameRepo, nameOwner).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t.toString())
                    onComplete(0)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("server", "${response}")
                    onComplete(response.code())
                }
            }
        )
    }
}