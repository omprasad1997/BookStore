package com.bridgelabz.UI.homedashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bridgelabz.Constants.Constants
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.bookList.BookFragment
import com.bridgelabz.UI.cart.CartFragment
import com.bridgelabz.UI.datamanager.BookDataManager
import com.bridgelabz.UI.login.LoginActivity
import com.bridgelabz.UI.orders.OrdersFragment
import com.bridgelabz.UI.profile.ProfileFragment
import com.bridgelabz.UI.wishlist.WishListFragment
import com.bridgelabz.bookstore.R


class HomeDashboardActivity : AppCompatActivity() {
    private val bookFragment = BookFragment()
    private val cartFragment = CartFragment()
    private val ordersFragment = OrdersFragment()
    private val wishListFragment = WishListFragment()
    private val profileFragment = ProfileFragment()
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var textCartItemCount: TextView
    private lateinit var bookDataManager: BookDataManager


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.home_dashboard_activity)
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookStore"
        sharedPreferenceHelper = SharedPreferenceHelper(this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, bookFragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.cart)

        val actionView: View = menuItem.actionView
        textCartItemCount = actionView.findViewById(R.id.cart_badge) as TextView

        setupBadge()

        bookFragment.updatingCartItemCount = {
            setupBadge()
        }

        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                setBookFragment(profileFragment)
                true
            }
            R.id.orders -> {
                setBookFragment(ordersFragment)
                return true
            }
            R.id.sign_out -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.sharedPreferenceHelper.setLoggedIn(false)
                finish()
                return true
            }
            R.id.cart -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragment_container,
                        cartFragment
                    ).addToBackStack(Constants.BACK_STACK_KEY_BOOK_LIST).commit()
                }
                return true
            }
            R.id.changeTheme -> {
                val isDarkModeOn = sharedPreferenceHelper.getDarkModeValue()
                 if (!isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    this.sharedPreferenceHelper.setDarkModeValue(true)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    this.sharedPreferenceHelper.setDarkModeValue(false)
                }
                return true
            }
            R.id.wish_list -> {
                setBookFragment(wishListFragment)
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

    private fun setupBadge() {
        bookDataManager = BookDataManager(this)
        val cartItemCount = bookDataManager.getCartItemBooks()

        textCartItemCount.text = cartItemCount.size.toString()
    }
}