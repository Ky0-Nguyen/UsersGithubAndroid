package com.androidgithubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.androidgithubusers.data.UserEntity
import com.androidgithubusers.models.UserDetail
import com.androidgithubusers.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for managing GitHub user data.
 *
 * This ViewModel is responsible for fetching and storing both the list of users
 * and detailed information about individual users. It uses a UserRepository to
 * fetch data from the API and exposes this data to the UI through LiveData.
 *
 * @property userRepository The repository used to fetch user data.
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    // LiveData for the list of users
    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> get() = _users

    // LiveData for detailed user information
    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> get() = _userDetail

    /**
     * Fetches a list of users from the API.
     *
     * @param since The ID of the last user fetched, used for pagination.
     */
    fun fetchUsers(since: Int) {
        viewModelScope.launch {
            val apiUsers = userRepository.fetchUsersFromApi(since)
            _users.postValue(apiUsers.map { user ->
                UserEntity(user.id, user.login, user.avatar_url, user.html_url)
            })
        }
    }

    /**
     * Fetches detailed information about a specific user.
     *
     * @param login The login name of the user to fetch details for.
     */
    fun getUserDetail(login: String) {
        viewModelScope.launch {
            val detail = userRepository.getUserDetail(login = login)
            _userDetail.postValue(detail)
        }
    }

    /**
     * Factory for creating UserViewModel instances.
     *
     * This factory allows us to create UserViewModel instances with a custom constructor
     * that includes the UserRepository.
     *
     * @property repository The UserRepository to be used in creating UserViewModel instances.
     */
    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
