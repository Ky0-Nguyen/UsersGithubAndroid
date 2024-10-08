package com.androidgithubusers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidgithubusers.data.UserDao
import com.androidgithubusers.data.UserEntity

/**
 * AppDatabase class represents the main database for the application.
 * It uses Room persistence library to manage SQLite database operations.
 *
 * @property entities Specifies the entities (tables) in the database.
 * @property version The version number of the database schema.
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
     /**
     * Provides access to the UserDao interface for database operations related to users.
     *
     * @return An instance of UserDao for performing CRUD operations on the user table.
     */
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}