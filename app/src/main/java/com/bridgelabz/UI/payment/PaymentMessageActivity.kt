package com.bridgelabz.UI.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.bridgelabz.bookstore.R

class PaymentMessageActivity : AppCompatActivity() {
    private lateinit var payButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_message)

        val paymentToolbar = findViewById<Toolbar>(R.id.paymentMessageToolbar)
        setSupportActionBar(paymentToolbar)
        supportActionBar?.title = "Payment"

        initView()
    }

    private fun initView() {
        payButton = findViewById(R.id.payButton)

        payButton.setOnClickListener {
            val intent  = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}