package com.jmonzon.minitwitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var tvGoSignUp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.buttonLogin)
        tvGoSignUp = findViewById(R.id.textViewSignUp)

        btnLogin.setOnClickListener(listener)
        tvGoSignUp.setOnClickListener(listener)

    }

    //Listener for elements in view
    private val listener= View.OnClickListener { view ->
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
        val intent = Intent  (this, SingUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}
