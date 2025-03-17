package com.chatapp.demo.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val uid: String = "",  // Default value to ensure no-arg constructor
    val email: String = "",
    val name: String = "",
    val phone: String = "",
    val online: Boolean = false,
    var lastMessage :String ="Start new Conversation"
) : Parcelable {
    constructor() : this("", "", "", "", false) // Explicit no-arg constructor
}
