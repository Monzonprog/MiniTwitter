package com.jmonzon.minitwitter.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    private var APP_SETTINGS_FILE: String = "APP_SETTINGS"

    private fun getSharedPreferences(): SharedPreferences {
        return MyApp.Companion.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE)
    }

    fun setStringValueSharedPreferences(dataLabel: String, dataValue: String) {
        with (getSharedPreferences().edit()) {
            putString(dataLabel, dataValue)
            commit()
        }

    }

}