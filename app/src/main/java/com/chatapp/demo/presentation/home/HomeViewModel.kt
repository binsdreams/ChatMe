package com.chatapp.demo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatapp.demo.domain.User
import com.chatapp.demo.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _UserResponses: MutableLiveData<List<User>> = MutableLiveData()
    val _UserResponsesLive: LiveData<List<User>> = _UserResponses

    fun getUsers(){
        repository.getUsers { usersList ->
            _UserResponses.value = usersList
        }
    }
}