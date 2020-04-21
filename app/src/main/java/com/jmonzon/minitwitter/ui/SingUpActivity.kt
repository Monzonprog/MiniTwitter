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
import com.jmonzon.minitwitter.models.RequestSignup
import com.jmonzon.minitwitter.models.ResponseAuth
import com.jmonzon.minitwitter.retrofit.MiniTwitterClient
import com.jmonzon.minitwitter.retrofit.MiniTwitterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingUpActivity : AppCompatActivity() {

    private lateinit var btnSignup: Button
    private lateinit var tvGoLogin: TextView
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var miniTwitterClient: MiniTwitterClient
    private lateinit var miniTwitterService: MiniTwitterService

    //Code necessary for register in API
    private var code: String = "UDEMYANDROID"

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Inicialize retrofit
        retrofitInit()
        //Includes all findByID and listener for diferents elements
        findViews()
        createEvents()
    }

    private fun retrofitInit() {
        miniTwitterClient = MiniTwitterClient().getInstance()
        miniTwitterService = MiniTwitterClient().miniTwitterService
    }

    private fun findViews() {
        btnSignup = findViewById(R.id.buttonSignup)
        tvGoLogin = findViewById(R.id.textViewGoLogin)
        etUsername = findViewById(R.id.editTextUserName)
        etEmail = findViewById(R.id.editTextEmail)
        etPassword = findViewById(R.id.editTextPassword)
    }

    private fun createEvents() {
        btnSignup.setOnClickListener(listener)
        tvGoLogin.setOnClickListener(listener)
    }

    //Listener for elements in view
    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonSignup -> {
                goToSignUp()
            }
            R.id.textViewGoLogin -> goToLogin()
        }
    }

    private fun goToSignUp() {
        val userName = etUsername.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        when {
            email.isEmpty() -> {
                etEmail.error = "Campo email es requerido"
            }
            password.isEmpty() || userName.length <= 4 -> {
                etPassword.error = "El password no puede estar vacío o tener más de 4 caracteres"
            }
            userName.isEmpty() -> {
                etUsername.error = "Campo usuario es requerido"
            }
            else -> {
                val requestSignup = RequestSignup(userName, email, password, code)
                val call: Call<ResponseAuth> = miniTwitterService.doSignUp(requestSignup)

                call.enqueue(object : Callback<ResponseAuth> {
                    override fun onResponse(
                        call: Call<ResponseAuth>,
                        response: Response<ResponseAuth>
                    ) {
                        if (response.isSuccessful) {
                            val i = Intent(applicationContext, DashboardActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                        Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }

    //Function to navigate to login Screen
    private fun goToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
