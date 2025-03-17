package com.chatapp.demo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.databinding.ItemUserBinding
import com.chatapp.demo.util.getColorForName

class UserAdapter(private val users: List<UserEntity>, private val onUserClick: (UserEntity) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.tvUserName.text = user.name
            val initials = user.name.take(2).uppercase()
            binding.tvIcon.text = initials
            binding.tvIcon.background.setTint(getColorForName(user.name))
            binding.userMessage.text = user.lastMessage.trim()
            binding.root.setOnClickListener { onUserClick(user) }
        }
    }
}