package com.kofar.appuk.data_user

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setSetting(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(USERNAME, value.username)
        editor.putString(PASSWORD, value.password)
        editor.apply()
    }

    fun getSetting(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.username = preferences.getString(USERNAME, "")
        model.password = preferences.getString(PASSWORD, "")
        return model
    }
}