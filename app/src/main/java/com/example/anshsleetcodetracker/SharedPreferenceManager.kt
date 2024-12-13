package com.example.anshsleetcodetracker

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_USERNAME = "username"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUsername(context: Context, username: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(context: Context): String? {
        return getPreferences(context).getString(KEY_USERNAME, null)
    }
}
