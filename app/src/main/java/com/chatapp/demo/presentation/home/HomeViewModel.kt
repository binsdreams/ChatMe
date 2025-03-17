package com.chatapp.demo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatapp.demo.data.db.UserEntity
import com.chatapp.demo.domain.repo.ChatRepository
import com.chatapp.demo.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UserRepository,
                                        private val chatRepository: ChatRepository) : ViewModel() {


    private val _chatListResponse: MutableLiveData<Boolean> = MutableLiveData()
    val _chatListResponseLive: LiveData<Boolean> =  _chatListResponse

    private val _userListResponse: MutableLiveData<List<UserEntity>> = MutableLiveData()
    val _userListResponseLive: LiveData<List<UserEntity>> =  _userListResponse

    fun getAllUsers(): LiveData<List<UserEntity>>{
        return repository.getAllUsers()
    }

    fun getUsers(){
        repository.getUsers()
    }

    fun getAllMessages(){
        chatRepository.getAllMessages{
            _chatListResponse.postValue(it)
        }
    }

    fun getMessageForEachUsers(users:List<UserEntity>){
        viewModelScope.launch {
            users.forEach { user ->
                val messageEntity = chatRepository.getLastMessageBetweenUsers(user.uid)
                messageEntity?.message?.let {
                    user.lastMessage = it
                }
                _userListResponse.postValue(users)
            }
        }
    }

}