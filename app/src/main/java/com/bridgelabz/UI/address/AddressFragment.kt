package com.bridgelabz.UI.address

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
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.UI.orders.OrderPlacedFragment
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class AddressFragment : Fragment() {
    private lateinit var mobileNumber: TextView
    private lateinit var flatNumber: TextView
    private lateinit var streetName: TextView
    private lateinit var cityName: TextView
    private lateinit var stateName: TextView
    private lateinit var pinCode: TextView
    private lateinit var pickAddressButton: Button
    private lateinit var addressToolbar: Toolbar
    private val TAG = "AddressFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_addess, container, false)

        initViews(view)
        onBackPressed()
        pickAddressButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container, OrderPlacedFragment()
            )?.addToBackStack(null)?.commit()
        }
        return view
    }

    private fun initViews(view: View) {

        mobileNumber = view.findViewById(R.id.mobile_number)
        flatNumber = view.findViewById(R.id.flat_number)
        streetName = view.findViewById(R.id.street_name)
        cityName = view.findViewById(R.id.city_name)
        stateName = view.findViewById(R.id.state_name)
        pinCode = view.findViewById(R.id.pin_code_number)
        pickAddressButton = view.findViewById(R.id.pick_address)
        addressToolbar = view.findViewById(R.id.address_toolbar)

        setViews(view)
    }

    private fun setViews(view: View) {
        val userModel = getUserAddressArray(view)
        Log.e(TAG, "Check User address : ${userModel!!.userAddress}")
        val user = userModel.userAddress[0]
        mobileNumber.text = user.mobile
        flatNumber.text = user.flat
        streetName.text = user.street
        cityName.text = user.city
        stateName.text = user.state
        pinCode.text = user.pinCode
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