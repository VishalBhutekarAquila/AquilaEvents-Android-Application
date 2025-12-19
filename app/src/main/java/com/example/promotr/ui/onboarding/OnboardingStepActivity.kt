package com.example.promotr.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.promotr.R
import com.example.promotr.MainActivity

class OnboardingStepActivity : AppCompatActivity() {

    // Add images array
    private val images = arrayOf(
        R.drawable.hireskilledworker,
        R.drawable.trustedmanpowerservices,
        R.drawable.quickbooking
    )

    private val titles = arrayOf(
        "Hire Skilled Professionals Anytime",
        "Trusted Manpower Services Near You",
        "Quick Booking & Real-Time Updates"
    )

    private val subtitles = arrayOf(
        "Connect with trusted experts for any project, big or small, right at your fingertips.",
        "Connect with skilled professionals in your local area for reliable and efficient services, whenever you need them.",
        "Effortlessly book skilled professionals and track your service status with real-time notifications, ensuring a seamless and efficient experience from start to finish."
    )

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_step)

        // Find ImageView
        val onboardingImageView = findViewById<ImageView>(R.id.onboardingImage)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val subtitleTextView = findViewById<TextView>(R.id.subtitleTextView)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val skipButton = findViewById<Button>(R.id.skipButton)
        val dot1 = findViewById<android.view.View>(R.id.dot1)
        val dot2 = findViewById<android.view.View>(R.id.dot2)
        val dot3 = findViewById<android.view.View>(R.id.dot3)

        fun updateDots() {
            dot1.isSelected = currentStep == 0
            dot2.isSelected = currentStep == 1
            dot3.isSelected = currentStep == 2

            // Animate dot selection for modern feel
            val dots = listOf(dot1, dot2, dot3)
            for ((i, dot) in dots.withIndex()) {
                dot.animate()
                    .scaleX(if (currentStep == i) 1.3f else 1f)
                    .scaleY(if (currentStep == i) 1.3f else 1f)
                    .setDuration(180)
                    .start()
            }
        }

        fun updateStep() {
            // Set image for current step
            onboardingImageView.setImageResource(images[currentStep])

            // Set text
            titleTextView.text = titles[currentStep]
            subtitleTextView.text = subtitles[currentStep]

            // Update dots
            updateDots()

            // Update button text on last screen
            if (currentStep == titles.size - 1) {
                nextButton.text = "Get Started"
            } else {
                nextButton.text = "Next"
            }
        }

        // Initialize first step
        updateStep()

        nextButton.setOnClickListener {
            if (currentStep < titles.size - 1) {
                currentStep++
                updateStep()
            } else {
                // Save onboarding completed status
                val sharedPref = getSharedPreferences("onboarding", MODE_PRIVATE)
                sharedPref.edit().putBoolean("completed", true).apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        skipButton.setOnClickListener {
            // Save onboarding completed status
            val sharedPref = getSharedPreferences("onboarding", MODE_PRIVATE)
            sharedPref.edit().putBoolean("completed", true).apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
