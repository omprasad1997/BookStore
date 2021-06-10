package com.bridgelabz.UI.homedashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bridgelabz.Constants.Constants
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.bookList.BookFragment
import com.bridgelabz.UI.cart.CartFragment
import com.bridgelabz.UI.login.LoginActivity
import com.bridgelabz.UI.orders.OrdersFragment
import com.bridgelabz.UI.wishlist.WishListFragment
import com.bridgelabz.bookstore.R

class HomeDashboardActivity : AppCompatActivity() {
    private val bookFragment = BookFragment()
    private val cartFragment = CartFragment()
    private val ordersFragment = OrdersFragment()
    private val wishListFragment = WishListFragment()
    private lateinit  var sharedPreferenceHelper:  SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.home_dashboard_activity)
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookStore"
        sharedPreferenceHelper =  SharedPreferenceHelper(this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, bookFragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(applicationContext, "click on profile", Toast.LENGTH_LONG).show()
                true
            }
            R.id.orders ->{
                setBookFragment(ordersFragment)
                Toast.makeText(applicationContext, "click on orders", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.sign_out ->{
                Toast.makeText(applicationContext, "click on Sign out", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.sharedPreferenceHelper.setLoggedIn(false)
                finish()
                return true
            }
            R.id.cart ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, cartFragment).addToBackStack(Constants.BACK_STACK_KEY_BOOK_LIST).
                    commit()
                }
                Toast.makeText(applicationContext, "click on cart", Toast.LENGTH_LONG).show()
                return true
            } R.id.wish_list ->{
                setBookFragment(wishListFragment)
                Toast.makeText(applicationContext, "click on wish list", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setBookFragment(bookFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, bookFragment).addToBackStack(null)
            commit()
        }
    }
}