package com.bridgelabz.UI.pickaddress

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.AddressResponseModel
import com.bridgelabz.UI.orders.OrderPlacedFragment
import com.bridgelabz.UI.register.UserDataManager
import com.bridgelabz.bookstore.R
import org.json.simple.JSONArray
import org.json.simple.JSONObject


class PickDeliveryAddressFragment : Fragment() {
    private lateinit var enteredMobileNumber: EditText
    private lateinit var enteredFlatNumber: EditText
    private lateinit var enteredStreetName: EditText
    private lateinit var enteredCityName: EditText
    private lateinit var enteredStateName: EditText
    private lateinit var enteredPinCode: EditText
    private lateinit var submitAddressButton: Button
    private lateinit var pickAddressToolbar: Toolbar
    private lateinit var addressResponseModel: AddressResponseModel
    private val TAG = "PickAddressFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.pick_delivery_address, container, false)

        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        enteredMobileNumber = view.findViewById(R.id.mobileNumber)
        enteredStreetName = view.findViewById(R.id.streetName)
        enteredFlatNumber = view.findViewById(R.id.flatNumber)
        enteredCityName = view.findViewById(R.id.cityName)
        enteredStateName = view.findViewById(R.id.stateName)
        enteredPinCode = view.findViewById(R.id.pinCodeNumber)
        submitAddressButton = view.findViewById(R.id.submitAddressButton)
        pickAddressToolbar = view.findViewById(R.id.pick_address_toolbar)

        submitAddressButton.setOnClickListener {
            pickAddress(it)
        }
    }


    private fun pickAddress(it: View) {
        val mobileNumber = enteredMobileNumber.text.toString()
        val flatNumber = enteredFlatNumber.text.toString()
        val streetName = enteredStreetName.text.toString()
        val cityName = enteredCityName.text.toString()
        val stateName = enteredStateName.text.toString()
        val pinCode = enteredPinCode.text.toString()

        if (!isMobileValid(mobileNumber)) {
            Toast.makeText(it.context, "Mobile Number is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isFlatNumberValid(flatNumber)) {
            Toast.makeText(it.context, "Flat Number is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isStreetValid(streetName)) {
            Toast.makeText(it.context, "Street name is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isCityValid(cityName)) {
            Toast.makeText(it.context, "City name is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isStateValid(stateName)) {
            Toast.makeText(it.context, "State name is not valid", Toast.LENGTH_LONG).show()
            return
        } else if (!isPinCodeValid(pinCode)) {
            Toast.makeText(it.context, "Pincode is not valid", Toast.LENGTH_LONG).show()
            return
        } else {
            addressResponseModel = AddressResponseModel(
                mobileNumber,
                flatNumber,
                streetName,
                cityName,
                stateName,
                pinCode
            )
            addDataTouUsersFile(it)
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container, OrderPlacedFragment()
            )?.commit()
        }
    }

    private fun addDataTouUsersFile(it: View) {
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
                val userAddressJsonObj = JSONObject()

                userAddressJsonObj["mobile"] = addressResponseModel.mobile
                userAddressJsonObj["flat"] = addressResponseModel.flat
                userAddressJsonObj["street"] = addressResponseModel.street
                userAddressJsonObj["city"] = addressResponseModel.city
                userAddressJsonObj["state"] = addressResponseModel.state
                userAddressJsonObj["pinCode"] = addressResponseModel.pinCode

                userAddress.add(userAddressJsonObj)

                val fos = it.context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
                fos.write(jsonArray.toString().toByteArray())
                fos.close()
                break
            }
        }
    }

    private fun isStreetValid(streetName: String): Boolean {
        if (streetName.isEmpty() || streetName.length < 3) {
            enteredStreetName.error = "Street name is not valid"
        } else {
            Log.e(TAG, "isStreetValid: true")
            return true
        }
        return false
    }

    private fun isPinCodeValid(pinCode: String): Boolean {
        if (pinCode.isEmpty() || pinCode.length != 6) {
            enteredPinCode.error = "Pin code is not valid"
        } else {
            Log.e(TAG, "isPinCodeValid: true")
            return true
        }
        return false
    }

    private fun isStateValid(stateName: String): Boolean {
        if (stateName.isEmpty() || stateName.length < 3) {
            enteredStateName.error = "State name is not valid"
        } else {
            Log.e(TAG, "isStateValid: true")
            return true
        }
        return false
    }

    private fun isCityValid(cityName: String): Boolean {
        if (cityName.isEmpty() || cityName.length < 3) {
            enteredCityName.error = "City name is not valid"
        } else {
            Log.e(TAG, "isCityValid: true")
            return true
        }
        return false
    }

    private fun isFlatNumberValid(flatNumber: String): Boolean {
        if (flatNumber.isEmpty() || flatNumber.length < 3) {
            enteredFlatNumber.error = "Mobile Number is not valid"
        } else {
            Log.e(TAG, "isFlatNumberValid: true")
            return true
        }
        return false
    }

    private fun isMobileValid(mobileNumber: String): Boolean {
        if (mobileNumber.isEmpty() || mobileNumber.length != 10) {
            enteredMobileNumber.error = "Mobile Number is not valid"
        } else {
            Log.e(TAG, "isMobileValid: true")
            return true
        }
        return false
    }

    private fun onBackPressed() {

        pickAddressToolbar.title = "Pick Delivery Address"
        pickAddressToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        pickAddressToolbar.setNavigationOnClickListener { //handle any click event
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