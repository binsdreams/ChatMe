package com.chatapp.demo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data Model for User
@Parcelize
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val online: Boolean = false,
    var message: Message?=null
):Parcelable

// Data Model for Messages
@Parcelize
data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
):Parcelable
