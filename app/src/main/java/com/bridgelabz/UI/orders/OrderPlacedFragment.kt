package com.bridgelabz.UI.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.bridgelabz.Constants.Constants
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.datamanager.BookDataManager
import com.bridgelabz.UI.model.responsemodel.CartResponseModel
import com.bridgelabz.UI.model.responsemodel.OrderResponseModel
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.UI.datamanager.UserDataManager
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OrderPlacedFragment : Fragment() {
    private lateinit var continueShoppingButton: Button
    private lateinit var orderToolbar: Toolbar
    private lateinit var orderIdTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private val TAG = "OrderPlacedFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_placed, container, false)
        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        orderIdTextView = view.findViewById(R.id.orderId)
        dateTextView = view.findViewById(R.id.date)
        timeTextView = view.findViewById(R.id.time)
        continueShoppingButton = view.findViewById(R.id.continueShoppingButton)
        orderToolbar = view.findViewById(R.id.order_toolbar)

        setViews()

        continueShoppingButton.setOnClickListener {
            addToOrders(it)
            parentFragmentManager.popBackStack(
                Constants.BACK_STACK_KEY_BOOK_LIST,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    private fun addToOrders(it: View) {
        val userDataManager =
            UserDataManager(it.context)
        val bookDataManager =
            BookDataManager(it.context)
        val sharedPreferenceHelper = SharedPreferenceHelper(it.context)
        val cartItems = bookDataManager.getCartItemBooks()

        val cartItemResponses = cartItems.map {
            CartResponseModel(
                it.bookQuantity,
                it.book.bookId
            )
        }
        val totalPrice = bookDataManager.getTotalPrice(cartItems)
        Log.e(TAG, "addToOrders: $totalPrice")
        val orderId = System.currentTimeMillis()
        val dateTimeString =
            SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(Date(orderId))

        val orderResponseModel =
            OrderResponseModel(
                orderId,
                totalPrice,
                cartItemResponses,
                dateTimeString
            )

        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

        val listOfUsers: ArrayList<UserModel> = Gson().fromJson(file.readText(), userListType)
        val user = listOfUsers.findLast {
            it.id == sharedPreferenceHelper.getLoggedInUserId()
        }

        user?.orderList?.add(orderResponseModel)

        user?.cartBookList = ArrayList()

        Log.e(TAG, "addToOrders: $listOfUsers ${file.path}")
        val fileWriter = FileWriter(file)
        fileWriter.use {
            Gson().toJson(listOfUsers, it)
        }

    }


    private fun setViews() {
        val currentTime = System.currentTimeMillis()
        orderIdTextView.text = currentTime.toString()
        dateTextView.text   = SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault()).format(Date(currentTime))
        timeTextView.text = SimpleDateFormat("hh:mm a ", Locale.getDefault()).format(Date(currentTime))
    }

    private fun onBackPressed() {

        orderToolbar.title = "Order"
        orderToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        orderToolbar.setNavigationOnClickListener { //handle any click event
            parentFragmentManager.popBackStack(Constants.BACK_STACK_KEY_BOOK_LIST, 0)
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