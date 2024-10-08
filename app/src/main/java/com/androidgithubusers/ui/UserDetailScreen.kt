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
import com.androidgithubusers.repository.UserRepository
import com.androidgithubusers.viewmodel.UserViewModel
import com.bumptech.glide.Glide

/**
 * UserDetailScreen is an Activity that displays detailed information about a GitHub user.
 *
 * This activity receives a user's login name via intent, fetches the user's details using
 * a ViewModel, and displays the information in the UI. It includes the user's avatar,
 * username, location, follower and following counts, and blog URL.
 */
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

        // Show user location container
        val userLocationContainer = findViewById<View>(R.id.user_location_container)
        userLocationContainer.visibility = View.VISIBLE

        // Set up back button
        setupBackButton(views.backButton)

        // Observe user detail data
        observeUserDetail(views)

        // Fetch user detail
        userDetailViewModel.getUserDetail(userLogin)
    }

    /**
     * Initializes the ViewModel used for fetching and storing user details.
     */
    private fun initializeViewModel() {
        val repository = UserRepository(
            BaseAPI.api
        )
        val viewModelFactory = UserViewModel.Factory(repository)
        userDetailViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

    /**
     * Initializes and returns a Views object containing references to all UI elements.
     *
     * @return Views object with references to UI elements
     */
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

    /**
     * Sets up the back button to finish the activity when clicked.
     *
     * @param backButton The ImageButton used as a back button
     */
    private fun setupBackButton(backButton: ImageButton) {
        backButton.setOnClickListener { finish() }
    }

    /**
     * Observes changes in user detail data and updates the UI accordingly.
     *
     * @param views Views object containing references to UI elements
     */
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
        }
    }

    /**
     * Formats a count for display, showing "100+" if the count exceeds the threshold.
     *
     * @param count The actual count
     * @param threshold The threshold above which to display "100+"
     * @return Formatted count as a string
     */
    private fun formatCount(count: Int, threshold: Int): String {
        return if (count > threshold) "${threshold}+" else count.toString()
    }

    /**
     * Data class to hold view references for easy access and organization.
     */
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