package com.bridgelabz.UI.cart

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.address.AddressFragment
import com.bridgelabz.UI.bookList.BookDataManager
import com.bridgelabz.UI.cart.adapter.CartAdapter
import com.bridgelabz.UI.pickaddress.PickDeliveryAddressFragment
import com.bridgelabz.UI.register.UserDataManager
import com.bridgelabz.bookstore.R
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class CartFragment : Fragment() {
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartToolbar: Toolbar
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartProceedToBuyButton: Button
    private var pickDeliveryAddressFragment = PickDeliveryAddressFragment()
    private var addressFragment = AddressFragment()
    private var TAG = "CartFragment"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        initViews(view)
        onBackPressed(view)
        return view
    }

    private fun initViews(view: View) {
        cartToolbar = view.findViewById(R.id.cart_toolbar)
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView)
        cartProceedToBuyButton = view.findViewById(R.id.proceedToBuy)
        setUpAdapter(view)
    }

    private fun setUpAdapter(view: View) {
        val bookDataManager = BookDataManager(view.context)
        cartRecyclerView.layoutManager = LinearLayoutManager(view.context) // require context
        val cartItemBookList = bookDataManager.getCartItemBooks()
        cartAdapter =
            CartAdapter(cartItemBookList!!)
        cartProceedToBuyButton.setOnClickListener {
            if (checkForAddress(it)) {
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.fragment_container, addressFragment
                )?.commit()
            } else {
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.fragment_container, pickDeliveryAddressFragment
                )?.commit()
            }
        }

        cartRecyclerView.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()
    }

    private fun checkForAddress(it: View): Boolean {
        val userDataManager = UserDataManager(it.context)
        val sharedPreferenceHelper = SharedPreferenceHelper(it.context)

        val jsonArray = userDataManager.readDataFromJSONFile()

        for (i in 0 until jsonArray.size) {
            val usersJSONObj = jsonArray[i] as JSONObject
            Log.e(
                TAG,
                "AddressChecked: ${sharedPreferenceHelper.getLoggedInUserId()} : $usersJSONObj"
            )
            if (sharedPreferenceHelper.getLoggedInUserId() == usersJSONObj["id"]) {
                val userAddress = usersJSONObj["usersAddressArray"] as JSONArray
                Log.e(TAG, "checkForAddress: ${userAddress.size}")
                if (userAddress.size != 0) return true
            }
        }
        return false
    }

    private fun onBackPressed(view: View) {

        cartToolbar.title = "Cart"
        cartToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        cartToolbar.setNavigationOnClickListener { //handle any click event
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

}