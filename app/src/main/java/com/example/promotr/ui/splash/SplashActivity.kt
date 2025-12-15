package com.example.promotr.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.promotr.R
import com.example.promotr.ui.onboarding.OnboardingActivity
import android.animation.ObjectAnimator
import android.animation.Animator
import android.widget.ProgressBar

/**
 * SplashActivity is the entry point. It checks internet and storage permissions.
 * - Guest mode, onboarding, and role-based logic would be handled after splash (see comments).
 * - Storage permission is needed for Room to cache data on device. If denied, fallback is explained in repository.
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.checkInternetAndProceed()
            } else {
                // If storage permission denied, fallback to in-memory cache (see repository comments)
                showPermissionDeniedDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SplashViewModel::class.java]

        // Request storage permission for Room caching
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Ask user for app-level internet consent (informational dialog). Android has no runtime permission for INTERNET.
                showInternetConsentDialog()
            } else {
            // Explain why permission is needed
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(this)
                    .setTitle("Storage Permission Required")
                    .setMessage("Promotr needs storage access to cache data for offline use.")
                    .setPositiveButton("OK") { _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        showPermissionDeniedDialog()
                    }
                    .show()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        viewModel.isInternetAvailable.observe(this) { available ->
            if (!available) {
                showNoInternetDialog()
            }
        }
        // Observe navigation. ViewModel decides whether to send user to onboarding or main.
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        viewModel.navigateToNext.observe(this) { go ->
            if (go == true) {
                // Animate progress bar to completion, then navigate based on stored state.
                try {
                    val animator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progressBar.max)
                    animator.duration = 600
                    animator.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}
                        override fun onAnimationEnd(animation: Animator) {
                            // Always show onboarding after splash
                            startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                            finish()
                        }
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    })
                    animator.start()
                } catch (t: Throwable) {
                    // Fallback: decide immediately
                    // Fallback: always navigate to onboarding
                    startActivity(Intent(this, OnboardingActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("Retry") { _, _ ->
                viewModel.checkInternetAndProceed()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun showInternetConsentDialog() {
        AlertDialog.Builder(this)
            .setTitle("Use Internet?")
            .setMessage("Promotr uses the internet to fetch latest promotions. Allow usage?")
            .setPositiveButton("Allow") { _, _ ->
                viewModel.checkInternetAndProceed()
            }
            .setNegativeButton("Continue Offline") { _, _ ->
                // User declined; we still proceed but repository will fall back to Room/offline behavior.
                viewModel.checkInternetAndProceed()
            }
            .setCancelable(false)
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Without storage permission, Promotr cannot cache data for offline use. The app will run in online-only mode.")
            .setPositiveButton("Continue") { _, _ ->
                viewModel.checkInternetAndProceed()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
