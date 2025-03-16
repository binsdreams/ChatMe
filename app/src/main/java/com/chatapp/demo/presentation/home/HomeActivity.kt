package com.chatapp.demo.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.demo.databinding.ActivityHomeBinding
import com.chatapp.demo.domain.User
import com.chatapp.demo.presentation.chat.ChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val mHomeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding
    private val users = mutableListOf<User>()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenForEvents()

        adapter = UserAdapter(users) { user ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("userId", user.uid)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        mHomeViewModel.getUsers()
    }

    private fun listenForEvents(){
        mHomeViewModel._UserResponsesLive.observe(this) { response ->
            users.clear()
            users.addAll(response)
            adapter.notifyDataSetChanged()
        }
    }
}