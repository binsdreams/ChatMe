package com.chatapp.demo.domain.repo

import androidx.lifecycle.LiveData
import com.chatapp.demo.data.db.UserEntity

interface UserRepository {

    fun getAllUsers(): LiveData<List<UserEntity>>

    fun getUsers()
}