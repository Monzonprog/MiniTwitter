package com.jmonzon.minitwitter.utils

import android.app.Application
import android.content.Context

abstract class MyApp : Application() {
    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getContext() : Context{
            return context
        }
    }
}