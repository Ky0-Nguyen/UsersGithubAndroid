package com.androidgithubusers.services

import com.androidgithubusers.models.User
import com.androidgithubusers.models.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun fetchUsers(
        @Query("per_page") perPage: Int = 20,
        @Query("since") since: Int
    ): List<User>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String
    ): UserDetail
}