package com.bridgelabz.UI.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.homedashboard.HomeDashboardActivity
import com.bridgelabz.UI.register.RegisterActivity
import com.bridgelabz.UI.datamanager.UserDataManager
import com.bridgelabz.bookstore.R
import org.json.simple.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var login: Button
    private var isLoginIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializationOfViews()
        checkLogin()
    }

    private fun initializationOfViews() {
        userEmail = findViewById(R.id.userEmail)
        userPassword = findViewById(R.id.userPassword)
        login = findViewById(R.id.login)
        sharedPreferenceHelper = SharedPreferenceHelper(this)
    }

    private fun userLogin() {
        val email = userEmail.text.toString()
        val password = userPassword.text.toString()

        val userDataManager = UserDataManager(this)
        val jsonArray = userDataManager.readDataFromJSONFile()

        for (i in 0 until jsonArray.size) {
            val obj = jsonArray[i] as JSONObject
            if (email == obj["email"] && password == obj["password"]) {
                isLoginIn = true
                break
            }
        }
    }

    private fun checkLogin() {
        login.setOnClickListener {
            userLogin()
            if (isLoginIn) {
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                this.sharedPreferenceHelper.setLoggedIn(true)
                finish()
            } else {
                Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun userRegistration(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}