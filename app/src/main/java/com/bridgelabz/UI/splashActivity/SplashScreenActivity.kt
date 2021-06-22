package com.bridgelabz.UI.splashActivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.login.LoginActivity
import com.bridgelabz.UI.homedashboard.HomeDashboardActivity
import com.bridgelabz.bookstore.R

class SplashScreenActivity : AppCompatActivity() {
    private var TIME_OUT: Long = 3000
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.WHITE
        }
        setContentView(R.layout.activity_splash_screen)
        sharedPreferenceHelper = SharedPreferenceHelper(this)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler(Looper.myLooper()!!).postDelayed({
            // You can declare your desire activity here to open after finishing splash screen. Like MainActivity
            val checkLogin = sharedPreferenceHelper.getLoggedIn()

            if (!checkLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, TIME_OUT)
    }
}