package com.jmonzon.minitwitter.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.models.RequestUserProfile
import com.jmonzon.minitwitter.models.ResponseUploadPhoto
import com.jmonzon.minitwitter.models.ResponseUserProfile
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterClient
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileRepository {

    private lateinit var authMiniTwitterService: AuthMiniTwitterService
    private lateinit var authMiniTwitterClient: AuthMiniTwitterClient
    private var userProfile: MutableLiveData<ResponseUserProfile> = MutableLiveData()
    private lateinit var photoProfile: MutableLiveData<String>

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        authMiniTwitterClient = AuthMiniTwitterClient().getInstance()
        authMiniTwitterService = AuthMiniTwitterClient().authMiniTwitterService
        userProfile = getProfile()
        photoProfile = MutableLiveData()
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

    fun updateProfile(requestUserProfile: RequestUserProfile): MutableLiveData<ResponseUserProfile> {
        val call: Call<ResponseUserProfile> =
            authMiniTwitterService.updateUserProfile(requestUserProfile)
        call.enqueue(object : Callback<ResponseUserProfile> {
            override fun onResponse(
                call: Call<ResponseUserProfile>,
                response: Response<ResponseUserProfile>
            ) {
                if (response.isSuccessful) {
                    userProfile.setValue(response.body())
                } else {
                    Toast.makeText(
                        MyApp.getContext(),
                        "No se ha podido actualizar el perfil",
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

    fun uploadPhoto(file: File) {

        //var file = File(photoPath)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/jpg"), file)
        val call: Call<ResponseUploadPhoto> = authMiniTwitterService.uploadProfilePhoto(requestBody)

        call.enqueue(object : Callback<ResponseUploadPhoto> {
            override fun onResponse(
                call: Call<ResponseUploadPhoto>,
                response: Response<ResponseUploadPhoto>
            ) {
                if (response.isSuccessful) {
                    SharedPreferencesManager().setStringValueSharedPreferences(
                        Constants.photoUrl,
                        response.body()!!.filename
                    )
                    photoProfile.value = response.body()!!.filename
                } else {
                    Toast.makeText(
                        MyApp.getContext(),
                        "No se ha podido subir la fotografía",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseUploadPhoto>, t: Throwable) {
                Toast.makeText(
                    MyApp.getContext(),
                    "Error en la conexión",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }
}