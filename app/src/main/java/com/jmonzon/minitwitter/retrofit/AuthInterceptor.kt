package com.jmonzon.minitwitter.retrofit

import com.jmonzon.minitwitter.utils.Constants
import com.jmonzon.minitwitter.utils.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String =
            SharedPreferencesManager().getStringValueSharedPreferences(Constants.tokenValue)

        val request: Request =
            chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build()

        return chain.proceed(request)
    }
}