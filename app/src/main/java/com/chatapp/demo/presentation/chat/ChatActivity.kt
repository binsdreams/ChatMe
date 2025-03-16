package com.chatapp.demo.presentation.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.chatapp.demo.R
import com.chatapp.demo.databinding.ActivityChatBinding
import com.chatapp.demo.domain.Message
import com.chatapp.demo.domain.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

const val USER_KEY = "user_object_key"
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

        // Find the Toolbar
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        // Set the Toolbar as the Action Bar
        this.setSupportActionBar(toolbar)

        val user = intent.getParcelableExtra<User>(USER_KEY) ?: return

        // Customize the Action Bar (optional)
        supportActionBar?.title =user.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val receiverId = user.uid
        val currentUserId = FirebaseAuth.getInstance().uid ?: ""
        adapter = MessageAdapter(messages,currentUserId)
        binding.recyclerView.adapter = adapter

        mChatViewModel.getMessages(receiverId)
        handleKeyboard(receiverId)
        binding.btnSend.setOnClickListener {
            sendMessage(receiverId)
        }
    }

    private fun sendMessage(receiverId :String){
        val messageText = binding.etMessage.text.toString()
        if (messageText.trim().isNotEmpty()) {
            val message = Message(
                FirebaseAuth.getInstance().uid!!,
                receiverId,
                messageText
            )
            mChatViewModel.sendMessage(message,receiverId);
            binding.etMessage.text?.clear()
        }
    }

    private fun handleKeyboard(receiverId :String){
        binding.etMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage(receiverId)
                true // Return true to indicate event is handled
            } else {
                false
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Handle back navigation
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}