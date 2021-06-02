package com.bridgelabz.UI.homedashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bridgelabz.bookstore.R

class HomeDashboardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.home_dashboard_activity)
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "BookStore"
    }
}