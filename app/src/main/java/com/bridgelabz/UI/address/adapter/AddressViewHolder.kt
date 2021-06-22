package com.bridgelabz.UI.address.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.responsemodel.AddressResponseModel
import com.bridgelabz.bookstore.R

class AddressViewHolder(view: View, pickAddressHandler: () -> Unit) :
    RecyclerView.ViewHolder(view) {

    private lateinit var addressResponseModel: AddressResponseModel
    private var TAG = "BookViewHolder"
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private val mobileNumberTextView = view.findViewById<TextView>(R.id.mobile_number_text_view)
    private val flatNumberTextView = view.findViewById<TextView>(R.id.flat_number_text_view)
    private val streetNameTextView = view.findViewById<TextView>(R.id.street_name_text_view)
    private val cityNameTextView = view.findViewById<TextView>(R.id.city_name_text_view)
    private val stateNameTextView = view.findViewById<TextView>(R.id.state_name_text_view)
    private val pinCodeNumberTextView = view.findViewById<TextView>(R.id.pin_code_number_text_view)
    private val pickAddressButton = view.findViewById<TextView>(R.id.pick_address_button)

    init {
        pickAddressButton.setOnClickListener {
            pickAddressHandler()
        }
    }


    fun bind(addressResponseModel: AddressResponseModel) {
        this.addressResponseModel = addressResponseModel
        mobileNumberTextView.text = addressResponseModel.mobile
        flatNumberTextView.text = addressResponseModel.flat
        streetNameTextView.text = addressResponseModel.street
        cityNameTextView.text = addressResponseModel.city
        stateNameTextView.text = addressResponseModel.state
        pinCodeNumberTextView.text = addressResponseModel.pinCode

    }
}
