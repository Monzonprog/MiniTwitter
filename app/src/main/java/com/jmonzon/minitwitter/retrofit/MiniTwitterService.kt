package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.models.RequestLogin
import com.jmonzon.minitwitter.models.RequestSignup
import com.jmonzon.minitwitter.models.ResponseAuth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MiniTwitterService {

    @POST("auth/login")
    fun doLogin(@Body requestLogin: RequestLogin): Call<ResponseAuth>

    @POST("auth/signup")
    fun doSignUp(@Body requestSignup: RequestSignup): Call<ResponseAuth>
}