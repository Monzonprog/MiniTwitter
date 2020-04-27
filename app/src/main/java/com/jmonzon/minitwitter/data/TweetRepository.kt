package com.jmonzon.minitwitter.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmonzon.minitwitter.models.Tweet
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterClient
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterService
import com.jmonzon.minitwitter.common.MyApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository {

    private lateinit var authMiniTwitterService: AuthMiniTwitterService
    private lateinit var authMiniTwitterClient: AuthMiniTwitterClient
    lateinit var allTweetsRecovered: LiveData<List<Tweet>>

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        authMiniTwitterClient = AuthMiniTwitterClient().getInstance()
        authMiniTwitterService = AuthMiniTwitterClient().authMiniTwitterService
        allTweetsRecovered = getAllTweets()
    }


    fun getAllTweets(): LiveData<List<Tweet>> {
        val data: MutableLiveData<List<Tweet>> = MutableLiveData()

        val call: Call<List<Tweet>> = authMiniTwitterService.getAllTweets()
        call.enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(
                call: Call<List<Tweet>>,
                response: Response<List<Tweet>>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                    Log.i("TweetRepository", "Se han recibido los tweets")
                } else {
                    Toast.makeText(
                        MyApp.getContext(),
                        "Ha ocurrido al recuperar los tweets",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Toast.makeText(
                    MyApp.getContext(),
                    "Error en la conexión",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        return data
    }
}