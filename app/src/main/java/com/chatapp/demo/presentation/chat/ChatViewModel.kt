package com.chatapp.demo.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatapp.demo.domain.Message
import com.chatapp.demo.domain.repo.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {
    private val _chatListResponse: MutableLiveData<List<Message>> = MutableLiveData()
    val _chatListResponseLive: LiveData<List<Message>> =  _chatListResponse

    fun sendMessage(message: Message,receiverId:String){
        repository.sendMessage(message){
            repository.getMessages(FirebaseAuth.getInstance().uid!!, receiverId) { messagesList ->
                _chatListResponse.value = messagesList
            }
        }
    }

    fun getMessages(receiverId: String){
        repository.getMessages(FirebaseAuth.getInstance().uid!!, receiverId) { messagesList ->
            _chatListResponse.value = messagesList
        }
    }
}