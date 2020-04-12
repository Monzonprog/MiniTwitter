package com.jmonzon.minitwitter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.retrofit.MiniTwitterClient
import com.jmonzon.minitwitter.retrofit.MiniTwitterService

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var tvGoSignUp: TextView
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var miniTwitterClient : MiniTwitterClient
    private lateinit var miniTwitterService: MiniTwitterService

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialized retrofit vars
        retrofitInit()
        //Includes all findByID and listener for diferents elements
        findViews()
        createEvents()
    }

    private fun findViews() {
        btnLogin = findViewById(R.id.buttonLogin)
        tvGoSignUp = findViewById(R.id.textViewSignUp)
        etEmail = findViewById(R.id.editTextEmail)
        etPassword = findViewById(R.id.editTextPassword)
    }

    private fun createEvents() {
        btnLogin.setOnClickListener(listener)
        tvGoSignUp.setOnClickListener(listener)
    }

    private fun retrofitInit() {
        miniTwitterClient = MiniTwitterClient().getInstance()!!
        miniTwitterService = MiniTwitterClient().miniTwitterService
  }

    //Listener for elements in view
    private val listener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.buttonLogin -> {
                Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
            }
            R.id.textViewSignUp -> {
                goSignUp()
            }
        }
    }

    //Function to navigate to SignUp Screen
    private fun goSignUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}
