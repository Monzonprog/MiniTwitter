package com.jmonzon.minitwitter.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.models.RequestCreateTweet
import com.jmonzon.minitwitter.models.Tweet
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterClient
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository {

    private lateinit var authMiniTwitterService: AuthMiniTwitterService
    private lateinit var authMiniTwitterClient: AuthMiniTwitterClient
    private var allTweetsRecovered: MutableLiveData<List<Tweet>> = MutableLiveData()

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        authMiniTwitterClient = AuthMiniTwitterClient().getInstance()
        authMiniTwitterService = AuthMiniTwitterClient().authMiniTwitterService
        allTweetsRecovered = getAllTweets()
    }

    fun getAllTweets(): MutableLiveData<List<Tweet>> {

        val call: Call<List<Tweet>> = authMiniTwitterService.getAllTweets()
        call.enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(
                call: Call<List<Tweet>>,
                response: Response<List<Tweet>>
            ) {
                if (response.isSuccessful) {
                    allTweetsRecovered.value = response.body()
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
        return allTweetsRecovered
    }

    fun createTweet(message: String) {

        val requestCreateTweet = RequestCreateTweet(message)

        val call: Call<Tweet> = authMiniTwitterService.createTweet(requestCreateTweet)

        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(
                call: Call<Tweet>,
                response: Response<Tweet>
            ) {
                if (response.isSuccessful) {
                    //Add in first place created tweet
                    val listAux: ArrayList<Tweet> = ArrayList()
                    listAux.add(response.body()!!)
                    for (tweet in allTweetsRecovered.value!!) {
                        listAux.add(Tweet(tweet))
                    }
                    allTweetsRecovered.value = listAux
                } else {
                    Toast.makeText(
                        MyApp.getContext(),
                        "Algo ha ido mal, por favor intentalo más tarde",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                Toast.makeText(
                    MyApp.getContext(),
                    "Erro en la conexión, ha ocurrido un error al crear el tweet",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}