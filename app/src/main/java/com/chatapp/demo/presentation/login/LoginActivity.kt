package com.chatapp.demo.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chatapp.demo.R
import com.chatapp.demo.databinding.ActivityLoginBinding
import com.chatapp.demo.presentation.home.HomeActivity
import com.chatapp.demo.presentation.signup.SignupActivity
import com.chatapp.demo.util.showProgressBar
import com.chatapp.demo.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    // ViewBinding for the activity
    private lateinit var binding: ActivityLoginBinding

    // ViewModel for handling login logic
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up UI listeners (e.g., button clicks)
        setupListeners()

        // Animate the logo when the activity starts
        animateLogo()

        // Set up the "Sign Up" clickable text
        setupSignUpText()

        // Observe the login response from the ViewModel
        observeLoginResponse()
    }

    /**
     * Set up listeners for UI elements (e.g., login button).
     */
    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            handleLoginClick()
        }
    }

    /**
     * handle the click of keyboard or button to login
     */
    private fun handleLoginClick(){
        // Get the email and password from the input fields
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        // Call the ViewModel to perform login
        if(loginViewModel.validateAllFields(email,password,resources)){
            showProgressBar(binding.progressbar.parentLayout)
            loginViewModel.callLogin(email, password)
        }
    }

    /**
     * Observe the login response from the ViewModel.
     * If login is successful, navigate to the HomeActivity.
     * If login fails, show a toast message.
     */
    private fun observeLoginResponse() {
        loginViewModel._loginResponsesLive.observe(this) { response ->
            if (response.status) {
                // Login successful: Navigate to HomeActivity
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // Close the LoginActivity
            } else {
                // Login failed: Show error message
                showSnackbar(binding.root,response.message?:"")
            }
        }
    }

    /**
     * Animate the logo from the center of the screen to the top.
     */
    private fun animateLogo() {
        // Wait for the layout to be drawn before starting the animation
        binding.mainapplogo.post {
            // Get the screen height and logo's top position
            val screenHeight = resources.displayMetrics.heightPixels
            val imageViewTop = binding.mainapplogo.top.toFloat()

            // Calculate the distance to move the logo from the center to the top
            val centerY = screenHeight / 2f
            val translateY = centerY - imageViewTop

            // Set the initial state of the logo (scaled up and centered)
            binding.mainapplogo.scaleX = 2f // Scale X to 2x
            binding.mainapplogo.scaleY = 2f // Scale Y to 2x
            binding.mainapplogo.translationY = translateY // Move to the center

            // Animate the logo back to its original size and position
            binding.mainapplogo.animate()
                .scaleX(1f) // Scale back to original size
                .scaleY(1f)
                .translationY(0f) // Move back to the original Y position
                .setDuration(600) // Animation duration in milliseconds
                .setInterpolator(AccelerateDecelerateInterpolator()) // Smooth animation
                .withEndAction {
                    // After the animation ends, show the login form and "Sign Up" text
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.noAccountTextView.visibility = View.VISIBLE
                }
                .start()
        }
    }

    /**
     * Set up the "Don't have an account? Sign Up" text with clickable "Sign Up".
     */
    private fun setupSignUpText() {
        // Get the full text from resources
        val fullText = getString(R.string.dontHaveanAccount)

        // Create a SpannableString to style parts of the text
        val spannableString = SpannableString(fullText)

        // Set the color of "Don't have an account?" to black
        spannableString.setSpan(
            ForegroundColorSpan(android.graphics.Color.BLACK),
            0, // Start index
            22, // End index
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Create a ClickableSpan for the "Sign Up" text
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click action: Show a toast message
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = android.graphics.Color.BLUE // Set the clickable text color to blue
                ds.isUnderlineText = true // Underline the clickable text
            }
        }

        // Apply the ClickableSpan to the "Sign Up" text
        spannableString.setSpan(
            clickableSpan,
            22, // Start index of "Sign Up"
            30, // End index of "Sign Up"
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the SpannableString to the TextView
        binding.noAccountTextView.text = spannableString

        // Enable clicking on the TextView
        binding.noAccountTextView.movementMethod = LinkMovementMethod.getInstance()
    }


}