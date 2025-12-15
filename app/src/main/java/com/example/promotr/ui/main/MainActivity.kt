package com.example.promotr.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity stub: the original MainActivity was removed per request.
 * Keeping a small stub to avoid build issues if referenced elsewhere.
 * This activity simply closes immediately with a short message.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "MainActivity removed — flow ends after onboarding", Toast.LENGTH_SHORT).show()
        finish()
    }
}
