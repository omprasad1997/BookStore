package com.bridgelabz.HelperClass

import android.content.Context
import android.content.SharedPreferences
import com.bridgelabz.Constants.Constants

class SharedPreferenceHelper(context: Context)  {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        Constants.BOOK_STORE_PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun setLoggedIn(value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(Constants.KEY, value)
            apply()
        }
    }

    fun getLoggedIn() = sharedPreferences.getBoolean(Constants.KEY, false)

    fun setLoggedInUserId(value: Long) {
        with(sharedPreferences.edit()) {
            putLong(Constants.LOGGED_IN_USER_ID, value)
            apply()
        }
    }

    fun getLoggedInUserId() = sharedPreferences.getLong(Constants.LOGGED_IN_USER_ID, -1)

    fun getDarkModeValue() = sharedPreferences.getBoolean(Constants.IS_DARK_MODE, false)

    fun setDarkModeValue(value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(Constants.IS_DARK_MODE, value)
            apply()
        }
    }
}