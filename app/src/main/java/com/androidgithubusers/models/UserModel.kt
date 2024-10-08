package com.androidgithubusers.models

data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String
)

data class UserDetail(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val location: String?,
    val followers: Int,
    val following: Int,
    val blog: String
)
