package com.chatapp.demo.presentation.chat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.demo.databinding.ActivityChatBinding
import com.chatapp.demo.domain.Message
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

//// Chat Activity Implementation
@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private val mChatViewModel by viewModels<ChatViewModel>()

    private lateinit var binding: ActivityChatBinding
    private val messages = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenForEvents()

        val receiverId = intent.getStringExtra("userId") ?: return
        val currentUserId = FirebaseAuth.getInstance().uid ?: ""
        adapter = MessageAdapter(messages,currentUserId)
        binding.recyclerView.adapter = adapter

        mChatViewModel.getMessages(receiverId)

        binding.btnSend.setOnClickListener {
            val messageText = binding.etMessage.text.toString()
            if (messageText.trim().isNotEmpty()) {
                val message = Message(
                    FirebaseAuth.getInstance().uid!!,
                    receiverId,
                    messageText
                )
                mChatViewModel.sendMessage(message,receiverId);
                binding.etMessage.text.clear()
            }
        }
    }

    private fun listenForEvents(){
        mChatViewModel._chatListResponseLive.observe(this) { messagesList ->
            messages.clear()
            messages.addAll(messagesList)
            adapter.setMessages(messagesList)
            adapter.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(messages.size - 1)
        }
    }
}