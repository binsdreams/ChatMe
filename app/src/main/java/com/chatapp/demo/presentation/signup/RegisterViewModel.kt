package com.chatapp.demo.presentation.signup

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatapp.demo.R
import com.chatapp.demo.domain.AuthResponse
import com.chatapp.demo.domain.repo.AuthRepository
import com.chatapp.demo.util.isValidEmail
import com.chatapp.demo.util.isValidName
import com.chatapp.demo.util.isValidPassword
import com.chatapp.demo.util.isValidPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _RegisterResponses: MutableLiveData<AuthResponse> = MutableLiveData()
    val _RegisterResponsesLive: LiveData<AuthResponse> = _RegisterResponses

    fun callToRegister( name: String,
                        email: String,
                        phone: String,
                        password: String){
        repository.registerUser(name, email, phone, password) { success, error ->
            _RegisterResponses.value = AuthResponse(success,error)
        }
    }

    /**
     * Validates all fields (name, email, phone, password).
     *
     * @param name The name to validate.
     * @param email The email address to validate.
     * @param phone The phone number to validate.
     * @param password The password to validate.
     * @param resources android resource to access the strings
     * @return `true` if all fields are valid, `false` otherwise.
     */
    fun validateAllFields(name: String, email: String, phone: String, password: String,resources: Resources): Boolean {
        if(isValidName(name).not()){
            _RegisterResponses.value = AuthResponse(false,resources.getString(R.string.error_name))
            return  false
        }else if(isValidEmail(email).not()){
            _RegisterResponses.value = AuthResponse(false,resources.getString(R.string.error_email))
            return  false
        }else if(isValidPhone(phone).not()){
            _RegisterResponses.value = AuthResponse(false,resources.getString(R.string.error_phone))
            return  false
        }else if(isValidPassword(password?:"").not()){
            _RegisterResponses.value = AuthResponse(false,resources.getString(R.string.error_password))
            return  false
        }
        return true
    }
}