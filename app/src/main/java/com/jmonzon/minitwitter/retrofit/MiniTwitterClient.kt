package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.common.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MiniTwitterClient {
    private var instance: MiniTwitterClient? = null

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var miniTwitterService: MiniTwitterService = retrofit.create(MiniTwitterService::class.java)

    fun getInstance(): MiniTwitterClient {
        return instance?.let { it } ?: run {
            instance = MiniTwitterClient()
            instance!!
        }
    }
}

