package com.chatapp.demo.di

import com.chatapp.demo.data.AuthRepositoryImpl
import com.chatapp.demo.data.ChatRepositoryImpl
import com.chatapp.demo.data.UserRepositoryImpl
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.domain.repo.ChatRepository
import com.chatapp.demo.domain.repo.UserRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        val firebaseApp = FirebaseApp.getInstance()
        return FirebaseFirestore.getInstance(firebaseApp)
    }

    @Provides
    @Singleton
    fun provideChatRepository(firestore: FirebaseFirestore): ChatRepository {
        return ChatRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(firestore)
    }
}
