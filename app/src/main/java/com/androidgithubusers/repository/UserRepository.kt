package com.androidgithubusers.repository

import androidx.lifecycle.LiveData
import com.androidgithubusers.data.UserDao
import com.androidgithubusers.data.UserEntity
import com.androidgithubusers.models.User
import com.androidgithubusers.models.UserDetail
import com.androidgithubusers.services.UserService

class UserRepository(
    private val api: UserService,
) {
    suspend fun fetchUsersFromApi(since: Int): List<User> {
        return api.fetchUsers(since = since)
    }

//    suspend fun saveUsersToLocal(users: List<UserEntity>) {
//        userDao.insertUsers(users)
//    }
//
//    suspend fun getUsersFromLocal(): LiveData<List<UserEntity>> {
//        return userDao.getAllUsers()
//    }

    suspend fun getUserDetail(login: String): UserDetail {
        return api.getUserDetail(login)
    }
}
