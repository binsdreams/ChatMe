package com.chatapp.demo.data.cache

import android.app.Application
import android.content.SharedPreferences


private const val PREFS_FILE_NAME = "secure_prefs"
private const val PREFS_TOKEN_KEY = "access_token"
class CacheManager(private val application: Application) {

    private var sharedPreferences : SharedPreferences? = null

    init {
        createSharedPreferences()
    }

    private fun createSharedPreferences(): SharedPreferences {
        val masterKey = androidx.security.crypto.MasterKey.Builder(application)
            .setKeyScheme(androidx.security.crypto.MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = androidx.security.crypto.EncryptedSharedPreferences.create(
            application,
            PREFS_FILE_NAME.plus(application.packageName),
            masterKey,
            androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences!!
    }

    fun saveUserId(accessToken:String){
        val editor = sharedPreferences?.edit()
        editor?.putString(PREFS_TOKEN_KEY, accessToken)
        editor?.apply()
    }

    fun clear(){
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    fun getUserId():String{
        return sharedPreferences?.getString(PREFS_TOKEN_KEY,"")!!
    }
}