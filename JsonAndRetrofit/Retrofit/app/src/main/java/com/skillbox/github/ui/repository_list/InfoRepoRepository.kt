package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoRepoRepository {
    fun checkRepoStatus(
        nameRepo: String,
        nameOwner: String,
        onError: (String) -> Unit,
        onComplete: (String) -> Unit
    ) {
        Network.githubApi.checkIsStarredOrNot(nameOwner, nameRepo).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onError(t.toString())
                    onComplete("")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    onComplete(response.body().toString())
                }
            }
        )

    }
}