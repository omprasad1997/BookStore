package com.bridgelabz.UI.orders

import android.content.Context
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
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.bookList.BookDataManager
import com.bridgelabz.UI.bookList.BookFragment
import com.bridgelabz.UI.model.OrderResponseModel
import com.bridgelabz.UI.register.UserDataManager
import com.bridgelabz.bookstore.R
import org.json.simple.JSONArray
import org.json.simple.JSONObject


class OrderPlacedFragment : Fragment() {
    private lateinit var continueShoppingButton: Button
    private lateinit var orderToolbar: Toolbar
    private lateinit var orderId: TextView
    private lateinit var date: TextView
    private lateinit var time: TextView
    private val TAG = "OrderPlacedFragmen"


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
        orderId = view.findViewById(R.id.orderId)
        date = view.findViewById(R.id.date)
        time = view.findViewById(R.id.time)
        continueShoppingButton = view.findViewById(R.id.continueShoppingButton)
        orderToolbar = view.findViewById(R.id.order_toolbar)

        setViews()

        continueShoppingButton.setOnClickListener {
//            addToOrders(it)
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container, BookFragment()
            )?.commit()
        }
    }

//    private fun addToOrders(it: View) {
//        val userDataManager = UserDataManager(it.context)
//        val bookDataManager = BookDataManager(it.context)
//        val sharedPreferenceHelper = SharedPreferenceHelper(it.context)
//
//        val jsonArray = userDataManager.readDataFromJSONFile()
//
//        for (i in 0 until jsonArray.size) {
//            val usersJSONObj = jsonArray[i] as JSONObject
//            Log.e(
//                TAG,
//                "Orders details: ${sharedPreferenceHelper.getLoggedInUserId()} : $usersJSONObj"
//            )
//            if (sharedPreferenceHelper.getLoggedInUserId() == usersJSONObj["id"]) {
//                val userOrderList = usersJSONObj["orderList"] as JSONArray
//                val userAddressJsonObj = JSONObject()
//
//                userOrderList["orderId"] = System.currentTimeMillis().toString()
//                userOrderList["orderTotal"] = orderResponseModel.orderTotal
//                userOrderList["cartItems"] = bookDataManager.getCartItemBooks()
//                userOrderList["orderDate"] = orderResponseModel.orderDate
//
//
//               userOrderList.add(userAddressJsonObj)
//
//                val fos = it.context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
//                fos.write(jsonArray.toString().toByteArray())
//                fos.close()
//                break
//            }
//        }
//    }

    private fun setViews() {
        orderId.text = System.currentTimeMillis().toString()
    }

    private fun onBackPressed() {

        orderToolbar.title = "Order"
        orderToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        orderToolbar.setNavigationOnClickListener { //handle any click event
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