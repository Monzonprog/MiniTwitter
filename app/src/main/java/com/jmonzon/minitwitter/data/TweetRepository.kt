package com.jmonzon.minitwitter.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.models.Likes
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
    private var favTweetsRecovered: MutableLiveData<List<Tweet>> = MutableLiveData()

    val userName: String =
        com.jmonzon.minitwitter.common.SharedPreferencesManager()
            .getStringValueSharedPreferences(com.jmonzon.minitwitter.common.Constants.userName)


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

    fun getFavsTweets(): MutableLiveData<List<Tweet>> {
        val newFavList: ArrayList<Tweet> = ArrayList()
        val itTweet: Iterator<Tweet> = allTweetsRecovered.value!!.iterator()

        while (itTweet.hasNext()) {
            val current: Tweet = itTweet.next()
            val itLike: Iterator<Likes> = current.likes.iterator()
            var enc = false
            while (itLike.hasNext() && !enc) {
                val likes: Likes = itLike.next()
                if (likes.username == userName) {
                    enc = true
                    newFavList.add(Tweet(current))
                }
            }
        }
        favTweetsRecovered.value = newFavList
        return favTweetsRecovered
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
                        //Add rest of tweet in the list
                        listAux.add(Tweet(tweet))
                    }
                    //Set the list using the MutableLiveData for refrest the recyclerView
                    allTweetsRecovered.value = listAux
                    getFavsTweets()
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

    fun likeTweet(idTweet: Int) {

        val call: Call<Tweet> = authMiniTwitterService.likeTweet(idTweet)

        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(
                call: Call<Tweet>,
                response: Response<Tweet>
            ) {
                if (response.isSuccessful) {
                    //Add in first place created tweet
                    val listAux: ArrayList<Tweet> = ArrayList()
                    for (tweet in allTweetsRecovered.value!!) {
                        //If found the element with the same ID it mean than we press on it and
                        // overwrite the element in the list
                        if (tweet.id == response.body()?.id) {
                            listAux.add(response.body()!!)
                        } else {
                            listAux.add(Tweet(tweet))
                        }
                    }
                    allTweetsRecovered.value = listAux
                    getFavsTweets()
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