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

class UserAdapter(private val onUserClick: (UserEntity) -> Unit) :
    ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view, onUserClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(
        itemView: View,
        private val onUserClick: (UserEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val userUrl: TextView = itemView.findViewById(R.id.user_url)

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