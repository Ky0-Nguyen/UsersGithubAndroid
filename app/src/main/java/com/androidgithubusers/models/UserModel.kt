package com.androidgithubusers.models

/**
 * Data class representing a GitHub user.
 *
 * @property id The unique identifier of the user.
 * @property login The username of the GitHub user.
 * @property avatar_url The URL of the user's avatar image.
 * @property html_url The URL of the user's GitHub profile.
 */
data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String
)

/**
 * Data class representing detailed information about a GitHub user.
 *
 * @property id The unique identifier of the user.
 * @property login The username of the GitHub user.
 * @property avatar_url The URL of the user's avatar image.
 * @property html_url The URL of the user's GitHub profile.
 * @property location The location of the user.
*/
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
