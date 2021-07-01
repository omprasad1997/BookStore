package com.bridgelabz.UI.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.datamanager.BookDataManager
import com.bridgelabz.UI.bookreview.BookReviewFragment
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.UI.orders.adapter.OrderAdapter
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class OrdersFragment : Fragment() {
    private lateinit var orderToolbar: Toolbar
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var totalOrderPriceTextView: TextView
    private val TAG = "OrderFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        orderToolbar = view.findViewById(R.id.order_toolbar)
        orderRecyclerView = view.findViewById(R.id.ordersRecyclerView)
        totalOrderPriceTextView = view.findViewById(R.id.total_price_in_orders)
        setUpAdapter(view)
    }

    private fun setUpAdapter(view: View) {
        val bookDataManager =
            BookDataManager(view.context)
        val bookList = bookDataManager.getBookList()

        val sharedPreferenceHelper = SharedPreferenceHelper(view.context)
        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

        val listOfUsers: ArrayList<UserModel> = Gson().fromJson(file.readText(), userListType)
        val user = listOfUsers.findLast {
            it.id == sharedPreferenceHelper.getLoggedInUserId()
        }

        val orderPriceList = user?.orderList?.map {
            it.orderTotal
        }

        val totalOrderPrice = orderPriceList?.sum()
        Log.e(TAG, "setUpAdapter: $totalOrderPrice")
        totalOrderPriceTextView.text = totalOrderPrice.toString()
        Log.e(TAG, "setUpAdapter: $orderPriceList")

        val orderList = user?.orderList
        val bundle = Bundle()
        val bookReviewFragment = BookReviewFragment()
        orderRecyclerView.layoutManager = LinearLayoutManager(view.context)
        orderRecyclerView.adapter = OrderAdapter(orderList, bookList) {
            bundle.putInt("BookId", it.cartItems[0].bookId)
            bookReviewFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container, bookReviewFragment
            )?.addToBackStack(null)?.commit()
        }

    }

    private fun onBackPressed() {
        orderToolbar.title = "Orders"
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