package com.skillbox.github.ui.repository_list

import com.skillbox.github.data.Network
import com.skillbox.github.data.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryFragmentRepository {
    //    получение доступных репозиториев
    suspend fun getPublicRepoInfo(): List<UsersRepository> {
//    запрос на полуение доступных репозиториев
        val response = withContext(Dispatchers.IO) { Network.githubApi.searchUsersRepositories().execute() }
        return response.body()!!
    }

    //    получение отмеченных репозиториев пользователем
    suspend fun getStarredRepo(): List<UsersRepository> {
        return withContext(Dispatchers.IO) { Network.githubApi.getStarredRepositories() }
    }
}