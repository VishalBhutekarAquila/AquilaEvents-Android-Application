package com.example.promotr.ui.splash

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.promotr.R
import com.example.promotr.ui.onboarding.OnboardingStepActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var progressStatusText: TextView
    private lateinit var prefs: SharedPreferences
    private val PERMISSION_PREF = "permission_prefs"
    private val PERMISSION_ASKED_KEY = "permission_asked"

    // Flag to prevent multiple navigation calls
    private var hasNavigated = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Start animation after permission handling
        startProgressAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SplashViewModel::class.java]

        progressStatusText = findViewById(R.id.progressStatusText)
        prefs = getSharedPreferences(PERMISSION_PREF, MODE_PRIVATE)

        // Observe internet availability
        viewModel.isInternetAvailable.observe(this) { available ->
            if (!available) {
                showNoInternetDialog()
            }
        }

        // Observe navigation trigger - ONLY navigate when animation completes
        viewModel.navigateToNext.observe(this) { go ->
            if (go == true && !hasNavigated) {
                hasNavigated = true
                navigateToNextScreen()
            }
        }

        // Check permission and start
        checkPermissionAndStart()
    }

    private fun checkPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startProgressAnimation()
        } else if (!prefs.getBoolean(PERMISSION_ASKED_KEY, false)) {
            showStoragePermissionDialog()
            prefs.edit().putBoolean(PERMISSION_ASKED_KEY, true).apply()
        } else {
            startProgressAnimation()
        }
    }

    private fun startProgressAnimation() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 0

        // Show first text immediately
        progressStatusText.text = "Work Smarter. Shine Brighter."

        // First segment: 0-33% (0-2000ms)
        val firstAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 33).apply {
            duration = 2000
            start()
        }

        firstAnimator.doOnEnd {
            // Second text at 2000ms
            progressStatusText.text = "Your crew. Your strength, your Aquila."

            // Second segment: 33-66% (2000-4000ms)
            val secondAnimator = ObjectAnimator.ofInt(progressBar, "progress", 33, 66).apply {
                duration = 2000
                start()
            }

            secondAnimator.doOnEnd {
                // Third text at 4000ms
                progressStatusText.text = "Find work that moves you"

                // Third segment: 66-100% (4000-6000ms)
                val thirdAnimator = ObjectAnimator.ofInt(progressBar, "progress", 66, 100).apply {
                    duration = 2000
                    start()
                }

                thirdAnimator.doOnEnd {
                    // Animation complete - NOW check internet and navigate
                    viewModel.checkInternetAndProceed()
                }
            }
        }
    }

    private fun navigateToNextScreen() {
        val onboardingPrefs = getSharedPreferences("onboarding", MODE_PRIVATE)
        val isOnboardingCompleted = onboardingPrefs.getBoolean("completed", false)

        val intent = if (isOnboardingCompleted) {
            // Navigate to MainActivity if onboarding is done
            Intent(this, OnboardingStepActivity::class.java)
        } else {
            // Navigate to Onboarding if first time
            Intent(this, com.example.promotr.MainActivity::class.java)
        }

        startActivity(intent)
        finish()
    }

    private fun showStoragePermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Storage Permission")
            .setMessage("Aquila needs storage access to cache data for a better experience.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton("Skip") { _, _ ->
                startProgressAnimation()
            }
            .setCancelable(false)
            .show()
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("Retry") { _, _ ->
                // Check internet again and proceed if animation already done
                viewModel.checkInternetAndProceed()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
