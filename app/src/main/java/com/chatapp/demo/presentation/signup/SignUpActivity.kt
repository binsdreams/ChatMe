package com.chatapp.demo.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.demo.R
import com.chatapp.demo.databinding.ActivitySignupBinding
import com.chatapp.demo.presentation.login.LoginActivity
import com.chatapp.demo.util.hideProgressBar
import com.chatapp.demo.util.showProgressBar
import com.chatapp.demo.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

// Signup Activity Implementation
@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val mRegisterViewModel by viewModels<RegisterViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenForEvents()
        binding.btnSignUp.setOnClickListener {
            val name = binding.etfullname.text.toString()
            val email = binding.etEmailAdress.text.toString()
            val phone = binding.etPhoneNumber.text.toString()
            val password = binding.etSignupPassword.text.toString()
            if(mRegisterViewModel.validateAllFields(name, email, phone, password,resources)){
                showProgressBar(binding.progressbar.parentLayout)
                mRegisterViewModel.callToRegister(name, email, phone, password);
            }
        }
    }

    private fun listenForEvents(){
        mRegisterViewModel._RegisterResponsesLive.observe(this) { response ->
            hideProgressBar(binding.progressbar.parentLayout)
            if (response.status) {
                showSnackbar(binding.root,getString(R.string.success_signup))
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
                finish()
            } else {
                showSnackbar(binding.root,response.message?:"")
            }
        }
    }
}