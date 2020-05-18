package com.jmonzon.minitwitter.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.models.RequestLogin
import com.jmonzon.minitwitter.models.ResponseAuth
import com.jmonzon.minitwitter.retrofit.MiniTwitterClient
import com.jmonzon.minitwitter.retrofit.MiniTwitterService
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.ui.dashboard.DashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var tvGoSignUp: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var miniTwitterClient: MiniTwitterClient
    private lateinit var miniTwitterService: MiniTwitterService

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyApp.setContext(this)

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
        miniTwitterClient = MiniTwitterClient().getInstance()
        miniTwitterService = MiniTwitterClient().miniTwitterService
    }

    //Listener for elements in view
    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonLogin -> {
                goLogin()
            }
            R.id.textViewSignUp -> {
                goSignUp()
            }
        }
    }

    //Function for login
    private fun goLogin() {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()

        when {
            email.isEmpty() -> {
                etEmail.error = "Campo email es requerido"
            }
            password.isEmpty() -> {
                etPassword.error = "Password requerido"
            }
            else -> {
                val requestLogin = RequestLogin(email, password)
                val call: Call<ResponseAuth> = miniTwitterService.doLogin(requestLogin)

                call.enqueue(object : Callback<ResponseAuth> {
                    override fun onResponse(
                        call: Call<ResponseAuth>,
                        response: Response<ResponseAuth>
                    ) {
                        if (response.isSuccessful) {
                            SharedPreferencesManager().setStringValueSharedPreferences(
                                Constants.tokenValue,
                                response.body()!!.token
                            )
                            SharedPreferencesManager().setStringValueSharedPreferences(
                                Constants.userName,
                                response.body()!!.username
                            )
                            SharedPreferencesManager().setStringValueSharedPreferences(
                                Constants.email,
                                response.body()!!.email
                            )
                            SharedPreferencesManager().setStringValueSharedPreferences(
                                Constants.photoUrl,
                                response.body()!!.photoUrl
                            )
                            SharedPreferencesManager().setStringValueSharedPreferences(
                                Constants.created,
                                response.body()!!.created
                            )
                            SharedPreferencesManager().setBooleanValueSharedPreferences(
                                Constants.active,
                                response.body()!!.active
                            )
                            Log.i(MainActivity::class.java.simpleName, "Datos salvados")

                            val i = Intent(applicationContext, DashboardActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Ha ocurrido un error al logarte, revisa tus datos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Problemas de conexión, por favor intentalo de nuevo más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
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
