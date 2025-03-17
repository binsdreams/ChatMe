package com.chatapp.demo.di

import android.content.Context
import androidx.room.Room
import com.chatapp.demo.data.AuthRepositoryImpl
import com.chatapp.demo.data.ChatRepositoryImpl
import com.chatapp.demo.data.UserRepositoryImpl
import com.chatapp.demo.data.db.AppDatabase
import com.chatapp.demo.data.db.ChatsDao
import com.chatapp.demo.data.db.UserDao
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.domain.repo.ChatRepository
import com.chatapp.demo.domain.repo.UserRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideChatRepository(firestore: FirebaseFirestore,chatsDao: ChatsDao): ChatRepository {
        return ChatRepositoryImpl(firestore,chatsDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore,userDao: UserDao): UserRepository {
        return UserRepositoryImpl(firestore,userDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
       return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideChatsDao(database: AppDatabase): ChatsDao {
        return database.chatsDao()
    }
}
