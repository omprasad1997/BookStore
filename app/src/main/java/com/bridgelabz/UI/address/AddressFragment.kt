package com.bridgelabz.UI.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.address.adapter.AddressAdapter
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.UI.orders.OrderPlacedFragment
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class AddressFragment : Fragment() {

    private lateinit var addressToolbar: Toolbar
    private lateinit var addressRecyclerView: RecyclerView
    private val TAG = "AddressFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_addess, container, false)

        initViews(view)
        onBackPressed()

        return view
    }

    private fun initViews(view: View) {
        addressToolbar = view.findViewById(R.id.address_toolbar)
        addressRecyclerView = view.findViewById(R.id.address_recycler_view)
        setViews(view)
    }

    private fun setViews(view: View) {
        val user = getUserAddressArray(view)
        Log.e(TAG, "Check User address : ${user!!.userAddress}")

        val userAddressList = user.userAddress
        addressRecyclerView.layoutManager = LinearLayoutManager(view.context)
        addressRecyclerView.adapter =
            AddressAdapter(userAddressList) {
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.fragment_container, OrderPlacedFragment()
                )?.addToBackStack(null)?.commit()
            }
    }

    private fun getUserAddressArray(view: View): UserModel? {
        val sharedPreferenceHelper = SharedPreferenceHelper(view.context)
        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<Array<UserModel>>() {}.type
        val usersList: Array<UserModel> = Gson().fromJson(file.readText(), userListType)
        return usersList.findLast {
            (sharedPreferenceHelper.getLoggedInUserId() == it.id)
        }
    }

    private fun onBackPressed() {
        addressToolbar.title = "Address"
        addressToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        addressToolbar.setNavigationOnClickListener { //handle any click event
            activity?.supportFragmentManager?.popBackStack()
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