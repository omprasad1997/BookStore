package com.bridgelabz.UI.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.UI.model.AddressResponseModel
import com.bridgelabz.UI.model.CartResponseModel
import com.bridgelabz.UI.model.OrderResponseModel
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.bookstore.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var enteredUserName: EditText
    private lateinit var enteredEmail: EditText
    private lateinit var enteredPassword: EditText
    private lateinit var enteredConfirmPassword: EditText
    private lateinit var register: Button
    private val TAG = "RegisterActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Register"
        initViews()
        setClickToRegisterButton()
    }

    private fun setClickToRegisterButton() {
        register.setOnClickListener {
            registerUser()
        }
    }

    private fun initViews() {
        enteredUserName = findViewById(R.id.userName)
        enteredEmail = findViewById(R.id.email)
        enteredPassword = findViewById(R.id.password)
        enteredConfirmPassword = findViewById(R.id.confirmPassword)
        register = findViewById(R.id.register)
    }

    private fun isUserNameValid(userName: String): Boolean {

        if (userName.isEmpty() || (userName.length < 3)) {
            enteredUserName.error = "Your name is not valid"
        } else {
            Log.e(TAG, "isUserNameValid: true" )
            return true
        }
        return false
    }


    private fun isEmailValid(email: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            enteredEmail.error = "Email is not valid"
        } else {
            return true
        }
        return false
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.isEmpty() || password.length < 7) {
            enteredPassword.error = "Password must be 7 characters"
        } else {
            return true
        }
        return false
    }

    private fun registerUser() {

        val userName = enteredUserName.text.toString()
        val userEmail = enteredEmail.text.toString()
        val userPassword = enteredPassword.text.toString()
        val userConfirmPassword = enteredConfirmPassword.text.toString()

        if (!isUserNameValid(userName)) {
            Toast.makeText(this, "User name is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isEmailValid(userEmail)) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isPasswordValid(userPassword)) {
            Toast.makeText(this, "Password is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (userConfirmPassword.isEmpty() || !userConfirmPassword.contains(userPassword)) {
            enteredConfirmPassword.error = "Password does not match"
        }else{
            checkAndWriteDataToJSONFile(userName, userEmail, userPassword, userConfirmPassword)
        }
    }

    private fun checkAndWriteDataToJSONFile(userName: String, userEmail: String, userPassword: String, userConfirmPassword: String) {
        val favouriteBookList = ArrayList<Int>()
        val cartBookList = ArrayList<CartResponseModel>()
        val userList = ArrayList<UserModel>()
        val userAddress = ArrayList<AddressResponseModel>()
        val orderList = ArrayList<OrderResponseModel>()

        val userRegistrationModelClass = UserModel(System.currentTimeMillis(),userName, userEmail, userPassword, userConfirmPassword, favouriteBookList, cartBookList,userAddress, orderList)
        userList.add(userRegistrationModelClass)
        val userDataManager = UserDataManager(applicationContext)

        val isRegisteredIn = userDataManager.checkingDataFromJSONFile(userRegistrationModelClass)

        checkRegistration(isRegisteredIn)

    }

    private fun checkRegistration(isRegisteredIn: Boolean) {
            if(isRegisteredIn){
                finish()
            }else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
    }

    fun alreadyRegistered(view: View) {
        finish()
    }
}