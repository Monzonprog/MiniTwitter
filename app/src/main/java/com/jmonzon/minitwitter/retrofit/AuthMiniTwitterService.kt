package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.models.RequestCreateTweet
import com.jmonzon.minitwitter.models.Tweet
import retrofit2.Call
import retrofit2.http.*

interface AuthMiniTwitterService {

    @GET("tweets/all")
    fun getAllTweets(): Call<List<Tweet>>

    @POST("tweets/create")
    fun createTweet(@Body requestCreateTweet: RequestCreateTweet): Call<Tweet>

    @POST("tweets/like/{idTweet}")
    fun likeTweet(@Path("idTweet")idTweet: Int): Call<Tweet>
}