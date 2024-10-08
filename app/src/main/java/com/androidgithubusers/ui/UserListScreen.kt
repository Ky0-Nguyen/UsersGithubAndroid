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
import com.androidgithubusers.data.AppDatabase
import com.androidgithubusers.data.UserDao
import com.androidgithubusers.repository.UserRepository
import com.androidgithubusers.viewmodel.UserViewModel

/**
 * UserListScreen is an Activity that displays a list of GitHub users.
 *
 * This activity fetches and displays a paginated list of GitHub users. It uses a RecyclerView
 * to efficiently display the user list and implements infinite scrolling to load more users
 * as the user scrolls down. It also provides navigation to a detailed view of each user.
 */
class UserListScreen : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private var isLoading = false
    private var lastUserId = 0
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        initializeViewModel()
        initializeRecyclerView()
        initializeProgressBar()
        observeUsersData()
        loadMoreUsers()
    }

    /**
     * Initializes the ViewModel used for fetching and storing user data.
     */
    private fun initializeViewModel() {
        val repository = UserRepository(BaseAPI.api)
        val viewModelFactory = UserViewModel.Factory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    /**
     * Initializes the RecyclerView and its adapter, and sets up the scroll listener for pagination.
     */
    private fun initializeRecyclerView() {
        userAdapter = UserAdapter { user ->
            navigateToUserDetail(user.login)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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
    }

    /**
     * Initializes the ProgressBar used to indicate loading state.
     */
    private fun initializeProgressBar() {
        progressBar = findViewById(R.id.progress_bar)
    }

    /**
     * Sets up an observer for the users data in the ViewModel.
     */
    private fun observeUsersData() {
        userViewModel.users.observe(this) { users ->
            isLoading = false
            progressBar.visibility = View.GONE
            if (users.isNotEmpty()) {
                lastUserId = users.last().id
                userAdapter.submitList(userAdapter.currentList + users)
            }
        }
    }

    /**
     * Initiates the loading of more users if not already loading.
     */
    private fun loadMoreUsers() {
        if (!isLoading) {
            isLoading = true
            progressBar.visibility = View.VISIBLE
            userViewModel.fetchUsers(lastUserId)
        }
    }

    /**
     * Navigates to the UserDetailScreen for the selected user.
     *
     * @param login The login name of the selected user.
     */
    private fun navigateToUserDetail(login: String) {
        val intent = Intent(this, UserDetailScreen::class.java).apply {
            putExtra("USER_LOGIN", login)
        }
        startActivity(intent)
    }
}