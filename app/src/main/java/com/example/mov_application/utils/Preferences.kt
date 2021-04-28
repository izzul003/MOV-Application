package com.example.mov_application.utils

import android.content.Context

class Preferences (val context: Context) {
    companion object {
        const val USER_PREFF = "USER_PREFF"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREFF, 0)

    fun setValues(key: String, value: String) {
        var editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}