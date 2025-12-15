package com.example.promotr.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.promotr.MainActivity
import com.example.promotr.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Ensure the status bar background is white and icons are dark (override any unexpected tint)
        try {
            window.statusBarColor = resources.getColor(com.example.promotr.R.color.white, theme)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } catch (e: Exception) {
            // ignore if API level or resources cause issues
        }

        val cardJobs = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardJobs)
        val cardStaff = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardStaff)
        val skip = findViewById<TextView>(R.id.skipTextView)

        val prefs = com.example.promotr.data.local.SharedPrefManager.getInstance(this)

        // Click listeners: only show toast and toggle visual selection. No navigation or persistence.
        var jobsSelected = false
        var staffSelected = false

        cardJobs.setOnClickListener {
            jobsSelected = !jobsSelected
            staffSelected = false
            if (jobsSelected) {
                cardJobs.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke)
                cardJobs.strokeColor = resources.getColor(R.color.brand_orange_dark, theme)
            } else {
                cardJobs.strokeWidth = 0
            }
            // reset other
            cardStaff.strokeWidth = 0
            staffSelected = false
            Toast.makeText(this, if (jobsSelected) "For Jobs selected" else "For Jobs unselected", Toast.LENGTH_SHORT).show()
        }

        cardStaff.setOnClickListener {
            staffSelected = !staffSelected
            jobsSelected = false
            if (staffSelected) {
                cardStaff.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke)
                cardStaff.strokeColor = resources.getColor(R.color.brand_orange_dark, theme)
            } else {
                cardStaff.strokeWidth = 0
            }
            // reset other
            cardJobs.strokeWidth = 0
            jobsSelected = false
            Toast.makeText(this, if (staffSelected) "For Staff selected" else "For Staff unselected", Toast.LENGTH_SHORT).show()
        }

        skip.setOnClickListener {
            prefs.setGuest(true)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
