package com.jmonzon.minitwitter.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.models.*
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterClient
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {

    private lateinit var authMiniTwitterService: AuthMiniTwitterService
    private lateinit var authMiniTwitterClient: AuthMiniTwitterClient
    private var userProfile: MutableLiveData<ResponseUserProfile> = MutableLiveData()

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        authMiniTwitterClient = AuthMiniTwitterClient().getInstance()
        authMiniTwitterService = AuthMiniTwitterClient().authMiniTwitterService
        userProfile = getProfile()
    }

    fun getProfile(): MutableLiveData<ResponseUserProfile> {

        val call: Call<ResponseUserProfile> = authMiniTwitterService.getUserProfile()
        call.enqueue(object : Callback<ResponseUserProfile> {
            override fun onResponse(
                call: Call<ResponseUserProfile>,
                response: Response<ResponseUserProfile>
            ) {
                if (response.isSuccessful) {
                    userProfile.value = response.body()
                    Log.i("ProfileRepository", "Se han recibido el perfil de usuario")
                } else {
                    Toast.makeText(
                        MyApp.getContext(),
                        "Ha ocurrido al recuperar el progile",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseUserProfile>, t: Throwable) {
                Toast.makeText(
                    MyApp.getContext(),
                    "Error en la conexión",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return userProfile
    }
}