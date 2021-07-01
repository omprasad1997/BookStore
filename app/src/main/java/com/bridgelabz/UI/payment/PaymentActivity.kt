package com.bridgelabz.UI.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.homedashboard.HomeDashboardActivity
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter

class PaymentActivity : AppCompatActivity() {
    lateinit var cardNumberEditText: EditText
    lateinit var expiryNumberEditText: EditText
    lateinit var cvvNumberEditText: EditText
    lateinit var makePaymentButton: Button
    lateinit var cancelButton:Button
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    var TAG = "PaymentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val paymentToolbar = findViewById<Toolbar>(R.id.paymentToolbar)
        setSupportActionBar(paymentToolbar)
        supportActionBar?.title = "Payment"

        initView()

    }

    private fun initView() {

        cardNumberEditText = findViewById(R.id.cardNumberEditText)
        expiryNumberEditText = findViewById(R.id.expiryNumberEditText)
        cvvNumberEditText = findViewById(R.id.cvvNumberEditText)
        makePaymentButton = findViewById(R.id.makePaymentButton)
        cancelButton = findViewById(R.id.cancelButton)

        sharedPreferenceHelper = SharedPreferenceHelper(this)


        makePaymentButton.setOnClickListener {
            checkCardDetails()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun checkCardDetails() {
        val cardNumber = cardNumberEditText.text.toString()
        val expiryNumber = expiryNumberEditText.text.toString()
        val cvvNumber = cvvNumberEditText.text.toString()

        if (cardNumber.length != 16) {
            cardNumberEditText.error = "Card Number should be 16 digit"
            return
        } else if (expiryNumber.length != 5) {
            expiryNumberEditText.error = "Expiry Number is not valid"
            return
        } else if (cvvNumber.length != 3) {
            cvvNumberEditText.error = "CVV Number is not valid"
            return
        } else {
            premiumUserEnable()
        }
    }

    private fun premiumUserEnable() {
        val path = this.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        Log.e(TAG, "loadSplashScreen: $file")

        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type
        val listOfUsers: ArrayList<UserModel> =
            Gson().fromJson(file.readText(), userListType)

        val fileWriter = FileWriter(file)

        Log.e(TAG, "loadSplashScreen: listOfUsers $listOfUsers")
        val user = listOfUsers.findLast {
            it.id == sharedPreferenceHelper.getLoggedInUserId()
        }

        user!!.isPremiumUser = true

        fileWriter.use {
            Gson().toJson(listOfUsers, it)
        }

        Toast.makeText(this, "Your have been successfully completed payment", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}