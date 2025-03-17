package com.chatapp.demo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.domain.repo.ChatRepository
import com.chatapp.demo.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UserRepository,
                                        private val chatRepository: ChatRepository) : ViewModel() {

    fun getAllUsers(): LiveData<List<UserEntity>>{
        return repository.getAllUsers()
    }

    fun getUsers(){
        repository.getUsers()
    }

}