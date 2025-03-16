package com.chatapp.demo.util

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.chatapp.demo.R
import com.google.android.material.snackbar.Snackbar
import androidx.core.graphics.toColorInt

/**
 * Displays a Snackbar with a custom red background and white text.
 *
 * @param view    The view to anchor the Snackbar (usually the root view of the activity).
 * @param message The message to display in the Snackbar.
 */
fun showSnackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(Color.RED)
    snackbar.setTextColor(Color.WHITE)
    // Set Snackbar at the top
    val snackbarView = snackbar.view
    val params = snackbarView.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.setMargins(0, 50, 0, 0) // Add top margin of 50dp
    snackbarView.layoutParams = params
    // Show the Snackbar
    snackbar.show()
}

fun showProgressBar(parentView :View){
    parentView.visibility = View.VISIBLE
    val lottieAnimationView :LottieAnimationView = parentView.findViewById(R.id.lottieAnimationView)
    lottieAnimationView.playAnimation()
}


fun hideProgressBar(parentView :View){
    parentView.visibility = View.GONE
    val lottieAnimationView :LottieAnimationView = parentView.findViewById(R.id.lottieAnimationView)
    lottieAnimationView.pauseAnimation()
}

fun getColorForName(name: String): Int {
    val colors = listOf(
        "#6200EE".toColorInt(),
        "#03DAC6".toColorInt(),
        "#FF0266".toColorInt(),
        "#FFD600".toColorInt()
    )
    return colors[name.hashCode() % colors.size]
}