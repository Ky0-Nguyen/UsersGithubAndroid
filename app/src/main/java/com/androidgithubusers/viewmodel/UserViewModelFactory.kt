package com.androidgithubusers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidgithubusers.repository.UserRepository
import com.androidgithubusers.viewmodel.UserViewModel

/**
 * Factory for creating UserViewModel instances.
 *
 * This class implements ViewModelProvider.Factory to provide custom instantiation
 * of UserViewModel objects with the required dependencies.
 *
 * @property userRepository The UserRepository instance to be injected into the UserViewModel.
 */
class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given class.
     *
     * @param modelClass The class of the ViewModel to create an instance of.
     * @return A new instance of the specified ViewModel.
     * @throws IllegalArgumentException if the ViewModel class is not supported.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
