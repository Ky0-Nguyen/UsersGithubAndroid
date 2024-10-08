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

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> get() = _users

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> get() = _userDetail

    fun fetchUsers(since: Int) {
        viewModelScope.launch {
                val apiUsers = userRepository.fetchUsersFromApi(since)
//            userRepository.saveUsersToLocal(apiUsers.map { user ->
//                    UserEntity(user.id, user.login, user.avatar_url, user.html_url)
//                })
                _users.postValue(apiUsers.map { user ->
                    UserEntity(user.id,user.login, user.avatar_url, user.html_url)
                })
        }
    }

    fun getUserDetail(login: String) {
        viewModelScope.launch {
            val detail = userRepository.getUserDetail(login = login)
            _userDetail.postValue(detail)
        }
    }

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
