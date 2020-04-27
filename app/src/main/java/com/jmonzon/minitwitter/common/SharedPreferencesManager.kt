package com.jmonzon.minitwitter.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    private var APP_SETTINGS_FILE: String = "APP_SETTINGS"

    private fun getSharedPreferences(): SharedPreferences {
        return MyApp.getContext()
            .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE)
    }

    fun setStringValueSharedPreferences(dataLabel: String, dataValue: String) {
        with(getSharedPreferences().edit()) {
            putString(dataLabel, dataValue)
            commit()
        }
    }

    fun setBooleanValueSharedPreferences(dataLabel: String, dataValue: Boolean) {
        with(getSharedPreferences().edit()) {
            putBoolean(dataLabel, dataValue)
            commit()
        }
    }

    fun getStringValueSharedPreferences(dataLabel: String): String {
        return getSharedPreferences().getString(dataLabel, null)!!
    }

    fun getBooleanValueSharedPreferences(dataLabel: String): Boolean {
        return getSharedPreferences().getBoolean(dataLabel, false)
    }

}