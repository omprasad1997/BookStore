package com.bridgelabz.UI.homedashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bridgelabz.UI.bookList.BookFragment
import com.bridgelabz.UI.cart.CartFragment
import com.bridgelabz.UI.wishlist.WishListFragment
import com.bridgelabz.bookstore.R

class HomeDashboardActivity : AppCompatActivity() {
    private val bookFragment = BookFragment()
    private val cartFragment = CartFragment()
    private val wishListFragment = WishListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.home_dashboard_activity)
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookStore"
        setBookFragment(bookFragment)
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
                Toast.makeText(applicationContext, "click on orders", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.sign_out ->{
                Toast.makeText(applicationContext, "click on Sign out", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.cart ->{
                setBookFragment(cartFragment)
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
            replace(R.id.fragment_container, bookFragment)
            commit()
        }
    }
}