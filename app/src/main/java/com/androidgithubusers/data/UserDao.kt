package com.androidgithubusers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
// * Data Access Object (DAO) for User-related database operations.
// * This interface defines methods to interact with the 'users' table in the database.
// */
@Dao
interface UserDao {
     /**
     * Inserts a list of users into the database.
     * If a user with the same primary key already exists, it will be replaced.
     *
     * @param users The list of UserEntity objects to be inserted or updated.
     */
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertUsers(users: List<UserEntity>)

    /**
     * Retrieves all users from the database.
     *
     * @return A LiveData object containing a list of all UserEntity objects in the database.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserEntity>
}

