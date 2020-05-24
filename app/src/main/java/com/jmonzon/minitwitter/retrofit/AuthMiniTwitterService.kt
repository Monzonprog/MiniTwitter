package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.models.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AuthMiniTwitterService {

    //Tweets
    @GET("tweets/all")
    fun getAllTweets(): Call<List<Tweet>>

    @POST("tweets/create")
    fun createTweet(@Body requestCreateTweet: RequestCreateTweet): Call<Tweet>

    @POST("tweets/like/{idTweet}")
    fun likeTweet(@Path("idTweet")idTweet: Int): Call<Tweet>

    @DELETE("tweets/{idTweet}")
    fun deleteTweet(@Path("idTweet")idTweet: Int): Call<TweetDeleted>

    //Users
    @GET("users/profile")
    fun getUserProfile():Call<ResponseUserProfile>

    @PUT("users/profile")
    fun updateUserProfile(@Body requestUserProfile: RequestUserProfile):Call<ResponseUserProfile>

    @Multipart
    @POST("users/uploadprofilephoto")
    fun uploadProfilePhoto(@Part("file\"; filename=\"photo.jpeg\"") file: RequestBody): Call<ResponseUploadPhoto>


}