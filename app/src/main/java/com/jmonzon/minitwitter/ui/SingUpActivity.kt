package com.jmonzon.minitwitter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.jmonzon.minitwitter.R

class SingUpActivity : AppCompatActivity() {

    private lateinit var btnSignup: Button
    private lateinit var tvGoLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignup = findViewById(R.id.buttonSignup)
        tvGoLogin = findViewById(R.id.textViewGoLogin)

        btnSignup.setOnClickListener(listener)
        tvGoLogin.setOnClickListener(listener)
    }

    //Listener for elements in view
    private val listener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.buttonSignup -> {
                Toast.makeText(this, "SignUp", Toast.LENGTH_LONG).show()
            }
            R.id.textViewGoLogin -> goToLogin()
        }
    }

    //Function to navigate to login Screen
    private fun goToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
