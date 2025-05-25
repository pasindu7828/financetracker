package com.example.budgetlyst

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to input fields
        val emailInput = findViewById<EditText>(R.id.email_input)
        val nameInput = findViewById<EditText>(R.id.name_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        // Get reference to the Register button
        val registerButton = findViewById<Button>(R.id.login_button)

        // "Already have an account? Sign In" link
        val signInLink = findViewById<TextView>(R.id.sign_up_link)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val username = nameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Email validation pattern
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

            val passwordPattern = "^(?=.*[A-Z])(?=.*[@#\$%^&+=!]).{6,}$".toRegex()


            // Basic empty check
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Email format check
            if (!email.matches(emailPattern)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Password format check
            if (!password.matches(passwordPattern)) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters, include 1 uppercase letter and 1 symbol",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Save to SharedPreferences
            val sharedPref = getSharedPreferences("UserCredentials", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("email", email)
                putString("username", username)
                putString("password", password)
                apply()
            }

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }

        signInLink.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }
}
