package com.androidgithubusers.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a GitHub user entity in the local database.
 *
 * This data class is used to store essential information about a GitHub user.
 * It is annotated with Room annotations to define the database table structure.
 *
 * @property id The unique identifier of the user.
 * @property login The username of the GitHub user.
 * @property avatar_url The URL of the user's avatar image.
 * @property html_url The URL of the user's GitHub profile.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String
)
