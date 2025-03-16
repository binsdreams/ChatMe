package com.chatapp.demo.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chatapp.demo.databinding.ItemMessageBinding
import com.chatapp.demo.domain.Message

class MessageAdapter(private val messages: MutableList<Message>, private val currentUserId: String) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            if (message.senderId == currentUserId) {
                binding.tvMessageRight.text = message.message
                binding.tvMessageLeft.visibility = View.GONE
                binding.tvMessageRight.visibility = View.VISIBLE
            } else {
                binding.tvMessageLeft.text = message.message
                binding.tvMessageLeft.visibility = View.VISIBLE
                binding.tvMessageRight.visibility = View.GONE
            }
        }
    }

    fun setMessages(messagesList: List<Message>) {
        messages.clear()
        messages.addAll(messagesList)
        notifyDataSetChanged()
    }
}
