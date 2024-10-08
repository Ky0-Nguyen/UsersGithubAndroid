package com.androidgithubusers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidgithubusers.R
import com.androidgithubusers.data.UserDao
import com.androidgithubusers.repository.UserRepository
import com.androidgithubusers.viewmodel.UserViewModel

class UserListScreen : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private var isLoading = false
    private var lastUserId = 0
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Initialize ViewModel
        val repository = UserRepository(BaseAPI.api)
        val viewModelFactory = UserViewModel.Factory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        // Initialize RecyclerView and Adapter
        userAdapter = UserAdapter { user ->
            navigateToUserDetail(user.login)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progress_bar)

        // Add scroll listener for pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                
                val userLocationView = findViewById<View>(R.id.user_location_container)
                userLocationView.visibility = View.GONE
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    loadMoreUsers()
                }
            }
        })


        // Observe users data
        userViewModel.users.observe(this) { users ->
            isLoading = false
            progressBar.visibility = View.GONE
            if (users.isNotEmpty()) {
                lastUserId = users.last().id
                userAdapter.submitList(userAdapter.currentList + users)
            }
        }

        // Fetch initial data
        loadMoreUsers()
    }

    private fun loadMoreUsers() {
        if (!isLoading) {
            isLoading = true
            progressBar.visibility = View.VISIBLE
            userViewModel.fetchUsers(lastUserId)
        }
    }

    private fun navigateToUserDetail(login: String) {
        val intent = Intent(this, UserDetailScreen::class.java).apply {
            putExtra("USER_LOGIN", login)
        }
        startActivity(intent)
    }
}