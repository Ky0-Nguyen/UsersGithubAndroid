package com.androidgithubusers.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.androidgithubusers.R
import com.androidgithubusers.data.UserDao
import com.androidgithubusers.repository.UserRepository
import com.androidgithubusers.viewmodel.UserViewModel
import com.bumptech.glide.Glide

class UserDetailScreen : AppCompatActivity() {
    private lateinit var userDetailViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // Get user login from intent
        val userLogin = intent.getStringExtra("USER_LOGIN") ?: return

        // Initialize ViewModel
        initializeViewModel()

        // Initialize views
        val views = initializeViews()

        // Hide user URL view
        views.userUrlView.visibility = View.GONE

        // Set up back button
        setupBackButton(views.backButton)

        // Observe user detail data
        observeUserDetail(views)

        // Fetch user detail
        userDetailViewModel.getUserDetail(userLogin)
    }

    private fun initializeViewModel() {
        val repository = UserRepository(
            BaseAPI.api
        )
        val viewModelFactory = UserViewModel.Factory(repository)
        userDetailViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    private fun initializeViews(): Views {
        val userInfoLayout = findViewById<ConstraintLayout>(R.id.userInfoLayout)
        return Views(
            avatarImageView = userInfoLayout.findViewById(R.id.user_avatar),
            usernameTextView = userInfoLayout.findViewById(R.id.user_name),
            locationTextView = userInfoLayout.findViewById(R.id.user_location),
            followersTextView = findViewById(R.id.followersCountTextView),
            followingTextView = findViewById(R.id.followingCountTextView),
            backButton = findViewById(R.id.backButton),
            blogTextView = findViewById(R.id.blogTextView),
            userUrlView = findViewById(R.id.user_url)
        )
    }

    private fun setupBackButton(backButton: ImageButton) {
        backButton.setOnClickListener { finish() }
    }

    private fun observeUserDetail(views: Views) {
        userDetailViewModel.userDetail.observe(this) { userDetail ->
            // Load avatar image
            Glide.with(this).load(userDetail.avatar_url).into(views.avatarImageView)

            // Set user information
            views.usernameTextView.text = userDetail.login
            views.followersTextView.text = formatCount(userDetail.followers, 100)
            views.followingTextView.text = formatCount(userDetail.following, 10)
            views.locationTextView.text = userDetail.location ?: "Not specified"
            views.blogTextView.text = userDetail.blog.takeIf { it.isNotBlank() } ?: "No blog specified"

            // TODO: Implement opening the user's GitHub profile in a web browser
            // viewProfileButton.setOnClickListener { ... }
        }
    }

    private fun formatCount(count: Int, threshold: Int): String {
        return if (count > threshold) "${threshold}+" else count.toString()
    }

    // Data class to hold view references
    private data class Views(
        val avatarImageView: ImageView,
        val usernameTextView: TextView,
        val locationTextView: TextView,
        val followersTextView: TextView,
        val followingTextView: TextView,
        val backButton: ImageButton,
        val blogTextView: TextView,
        val userUrlView: View
    )
}