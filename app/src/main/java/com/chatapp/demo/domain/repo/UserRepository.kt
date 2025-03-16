package com.chatapp.demo.domain.repo

import com.chatapp.demo.domain.User

interface UserRepository {

    fun getUsers(onResult: (List<User>) -> Unit)
}