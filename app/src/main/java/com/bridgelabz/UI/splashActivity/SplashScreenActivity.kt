package com.bridgelabz.UI.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bridgelabz.UI.Login.LoginActivity
import com.bridgelabz.bookstore.R

class SplashScreenActivity : AppCompatActivity() {
    private var TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler(Looper.myLooper()!!).postDelayed({
            // You can declare your desire activity here to open after finishing splash screen. Like MainActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
//            val checkLogin = sharedPreferenceHelper.getLoggedIn()
//
//            if(!checkLogin) {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else {
//                val intent = Intent(this, HomeDashboardActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        },TIME_OUT)
    }
}