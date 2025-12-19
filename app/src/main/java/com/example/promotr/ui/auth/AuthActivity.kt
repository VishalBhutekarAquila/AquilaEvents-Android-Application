package com.example.promotr.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.promotr.R
import com.example.promotr.ui.main.MainActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var btnLogin: TextView
    private lateinit var btnSignUp: TextView
    private lateinit var btnAction: Button
    private lateinit var toggleContainer: View
    private var isLoginSelected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginsignup)

        // Initialize views
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnAction = findViewById(R.id.btnAction)
        toggleContainer = findViewById(R.id.toggleContainer)

        // Set up toggle buttons
        setupToggleButtons()

        // Set up action button
        setupActionButton()
    }

    private fun setupToggleButtons() {
        btnLogin.setOnClickListener {
            if (!isLoginSelected) {
                toggleSelection()
            }
        }

        btnSignUp.setOnClickListener {
            if (isLoginSelected) {
                toggleSelection()
            }
        }
    }

    private fun toggleSelection() {
        isLoginSelected = !isLoginSelected

        if (isLoginSelected) {
            // Switch to Login
            btnLogin.background = ContextCompat.getDrawable(this, R.drawable.bg_toggle_button)
            btnLogin.setTextColor(Color.WHITE)
            btnSignUp.background = null
            btnSignUp.setTextColor(Color.BLACK)
            btnAction.text = "Login"
            toggleContainer.setBackgroundResource(R.drawable.bg_toggle_selector)
        } else {
            // Switch to Sign Up
            btnSignUp.background = ContextCompat.getDrawable(this, R.drawable.bg_toggle_button)
            btnSignUp.setTextColor(Color.WHITE)
            btnLogin.background = null
            btnLogin.setTextColor(Color.BLACK)
            btnAction.text = "Sign Up"
            toggleContainer.setBackgroundResource(R.drawable.bg_toggle_selector)
        }
    }

    private fun setupActionButton() {
        btnAction.setOnClickListener {
            // Handle login/signup logic here
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}