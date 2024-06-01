package com.bridgelabz.UI.splashActivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.login.LoginActivity
import com.bridgelabz.UI.homedashboard.HomeDashboardActivity
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.UI.payment.PaymentMessageActivity
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter

class SplashScreenActivity : AppCompatActivity() {
    private val TAG = "SplashScreen"
    private var TIME_OUT: Long = 3000
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = Color.WHITE
//        }
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
                return@postDelayed
            }

            val path = this.filesDir?.absolutePath
            val file = File(path, "use_credential.json")
            Log.e(TAG, "loadSplashScreen: $file")

            val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type
            val listOfUsers: ArrayList<UserModel> =
                Gson().fromJson(file.readText(), userListType)

            Log.e(TAG, "loadSplashScreen: listOfUsers $listOfUsers")
            val user = listOfUsers.findLast {
                it.id == sharedPreferenceHelper.getLoggedInUserId()
            }

            if (user!!.freeTrial > 0 || user.isPremiumUser) {
                user.freeTrial = user.freeTrial - 1

                val fileWriter = FileWriter(file)
                fileWriter.use {
                    Gson().toJson(listOfUsers, it)
                }

                Log.e(TAG, "loadSplashScreen: $user")
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, PaymentMessageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, TIME_OUT)
    }
}
