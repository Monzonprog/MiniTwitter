package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthMiniTwitterClient {
    private var instance: AuthMiniTwitterClient? = null

    //Include header with token in petition
    private var okHttpClientBuilder : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
    private var client : OkHttpClient= okHttpClientBuilder.build()


    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    var authMiniTwitterService: AuthMiniTwitterService = retrofit.create(AuthMiniTwitterService::class.java)

    fun getInstance(): AuthMiniTwitterClient {
        return instance?.let { it } ?: run {
            instance = AuthMiniTwitterClient()
            instance!!
        }
    }
}

