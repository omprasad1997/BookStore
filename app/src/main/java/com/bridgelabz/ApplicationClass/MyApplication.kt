package com.bridgelabz.ApplicationClass

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.bridgelabz.HelperClass.SharedPreferenceHelper

class MyApplication : Application() {
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate() {
        super.onCreate()

        sharedPreferenceHelper = SharedPreferenceHelper(applicationContext)

        val isDarkModeOn = sharedPreferenceHelper.getDarkModeValue()
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}