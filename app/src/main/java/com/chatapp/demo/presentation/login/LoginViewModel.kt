package com.chatapp.demo.presentation.login

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatapp.demo.R
import com.chatapp.demo.domain.AuthResponse
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.util.isValidEmail
import com.chatapp.demo.util.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _LoginResponses: MutableLiveData<AuthResponse> = MutableLiveData()
    val _loginResponsesLive: LiveData<AuthResponse> = _LoginResponses

    fun callLogin(email: String, password: String){
        repository.loginUser(email, password) { success, error ->
            _LoginResponses.value = AuthResponse(success,error)
        }
    }

    /**
     * Validates all fields (email, password).
     *
     * @param email The email address to validate.
     * @param password The password to validate.
     * @param resources android resource to access the strings
     * @return `true` if all fields are valid, `false` otherwise.
     */
    fun validateAllFields(email: String,  password: String?,resources: Resources): Boolean {
        if(isValidEmail(email).not()){
            _LoginResponses.value = AuthResponse(false,resources.getString(R.string.error_email))
            return  false
        }else if(isValidPassword(password?:"").not()){
            _LoginResponses.value = AuthResponse(false,resources.getString(R.string.error_password))
            return  false
        }
        return true
    }

    fun isUserLoggedIn() :Boolean{
        return repository.isUserLoggedIn()
    }

    fun clearAndLogout() {
        repository.clearAndLogout()
    }
}