package com.chatapp.demo

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatMeApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this);
    }
}
