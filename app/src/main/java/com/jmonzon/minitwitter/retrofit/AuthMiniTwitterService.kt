package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.models.Tweet
import retrofit2.Call
import retrofit2.http.GET

interface AuthMiniTwitterService {

    @GET("tweets/all")
    fun getAllTweets(): Call<List<Tweet>>
}