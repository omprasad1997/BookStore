package com.bridgelabz.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bridgelabz.UI.homedashboard.HomeDashboardActivity
import com.bridgelabz.UI.register.RegisterActivity
import com.bridgelabz.UI.register.UserDataManager
import com.bridgelabz.bookstore.R

class LoginActivity : AppCompatActivity() {
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var login: Button
    private var isLoginIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializationOfViews()
        checkLogin()
    }

    private fun initializationOfViews() {
        userEmail    = findViewById(R.id.userEmail)
        userPassword = findViewById(R.id.userPassword)
        login        = findViewById(R.id.login)

    }

//    private fun isEmailValid(email: String): Boolean = email.isEmpty() || !email.contains("@gmail.com")
//    private fun isPasswordValid(password: String): Boolean = password.isEmpty()

    private fun userLogin() {
        val email = userEmail.text.toString()
        val password = userPassword.text.toString()
//        when {
//            isEmailValid(email) -> {
//                userEmail.error = "Email is not valid"
//            }
//            isPasswordValid(password) -> {
//                userPassword.error = "Password is incorrect"
//            }
//        }
        val userDataManager = UserDataManager(this)
        val jsonArray = userDataManager.readDataFromJSONFile()

        for(i in 0 until jsonArray!!.length()){
            val obj = jsonArray.getJSONObject(i)
            if(email == obj.getString("email") &&  password == obj.getString("password")){
                isLoginIn = true
                break
            }
        }
    }

    private fun checkLogin() {
        login.setOnClickListener {
            userLogin()
           if(isLoginIn){
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this , "Email or Password is incorrect",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun userRegistration(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}