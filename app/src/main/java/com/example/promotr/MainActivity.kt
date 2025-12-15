package com.example.promotr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Root MainActivity stub. Kept as a harmless stub so code referring to it doesn't fail.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "MainActivity removed — no UI", Toast.LENGTH_SHORT).show()
        finish()
    }
}