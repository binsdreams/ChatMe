package com.chatapp.demo.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chatapp.demo.R
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.databinding.ActivityHomeBinding
import com.chatapp.demo.presentation.chat.ChatActivity
import com.chatapp.demo.presentation.chat.USER_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val mHomeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding
    private val users = mutableListOf<UserEntity>()
    private lateinit var adapter: UserAdapter

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenForEvents()

        // Find the Toolbar
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        // Set the Toolbar as the Action Bar
        this.setSupportActionBar(toolbar)

        // Customize the Action Bar (optional)
        supportActionBar?.title = getString(R.string.app_name)// Set title
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        adapter = UserAdapter(users) { user ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(USER_KEY, user)
            startActivity(intent)
        }

        val dividerDrawable: Drawable? = getDrawable(R.drawable.divider)
        if (dividerDrawable != null) {
            val divider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
            divider.setDrawable(dividerDrawable)
            binding.recyclerView.addItemDecoration(divider)
        }

        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        mHomeViewModel.getUsers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun listenForEvents(){
        mHomeViewModel.getAllUsers().observe(this) { response ->
            users.clear()
            users.addAll(response)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}