package com.androidgithubusers.ui

import UserDiffCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidgithubusers.R
import com.androidgithubusers.data.UserEntity
import com.bumptech.glide.Glide

/**
 * Adapter for displaying a list of GitHub users in a RecyclerView.
 *
 * This adapter extends ListAdapter to efficiently handle updates to the list of users.
 * It uses UserDiffCallback to determine changes in the list.
 *
 * @property onUserClick A lambda function that is called when a user item is clicked.
 */
class UserAdapter(private val onUserClick: (UserEntity) -> Unit) :
    ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    /**
     * Creates a new ViewHolder for a user item view.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new UserViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view, onUserClick)
    }

    /**
     * Binds the data at the specified position to the ViewHolder.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder class for caching views used in the user item layout.
     *
     * @property itemView The root view of the user item layout.
     * @property onUserClick A lambda function to handle user item clicks.
     */
    class UserViewHolder(
        itemView: View,
        private val onUserClick: (UserEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val userUrl: TextView = itemView.findViewById(R.id.user_url)

        /**
         * Binds user data to the views in the ViewHolder.
         *
         * @param user The UserEntity object containing the data to be displayed.
         */
        fun bind(user: UserEntity) {
            userName.text = user.login
            userUrl.text = user.html_url
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .into(userAvatar)

            itemView.setOnClickListener { onUserClick(user) }
        }
    }
}