package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.data.Network
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoRepoRepository {
    //    проверка отмеченности репозитория пользователем
    suspend fun checkRepoStatus(
        nameRepo: String,
        nameOwner: String,
        onError: (String) -> Unit,
        onComplete: (Int) -> Unit
    ) {
        suspendCancellableCoroutine<Unit> { continuation ->
            continuation.invokeOnCancellation {
                Network.githubApi.checkIsStarredOrNot(nameRepo, nameOwner).isCanceled
            }
            //    выполнение запроса на проверку
            Network.githubApi.checkIsStarredOrNot(nameRepo, nameOwner).enqueue(
//            создание Callback объекта
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

    //    проставление отметки репозиотрию
    suspend fun addStar(
        nameRepo: String,
        nameOwner: String
    ) {
        Network.githubApi.addStar(nameRepo, nameOwner)
    }

    //    удаление отметки для репозитория
    suspend fun delStar(
        nameRepo: String,
        nameOwner: String
    ) {
        Network.githubApi.delStar(nameRepo, nameOwner)
    }
}