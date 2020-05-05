package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.models.RequestCreateTweet
import com.jmonzon.minitwitter.models.Tweet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthMiniTwitterService {

    @GET("tweets/all")
    fun getAllTweets(): Call<List<Tweet>>

    @POST("tweets/create")
    fun createTweet(@Body requestCreateTweet: RequestCreateTweet): Call<Tweet>
}