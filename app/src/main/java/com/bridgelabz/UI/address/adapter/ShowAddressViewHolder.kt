package com.bridgelabz.UI.address.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.responsemodel.AddressResponseModel
import com.bridgelabz.bookstore.R

class ShowAddressViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private val profileShowAddressMobileNumberTextView =
        view.findViewById<TextView>(R.id.profile_show_address_mobile_number_text_view)
    private val profileShowAddressFlatNumberTextView =
        view.findViewById<TextView>(R.id.profile_show_address_flat_number_text_view)
    private val profileShowAddressStreetNameTextView =
        view.findViewById<TextView>(R.id.profile_show_address_street_name_text_view)
    private val profileShowAddressCityNameTextView =
        view.findViewById<TextView>(R.id.profile_show_address_city_name_text_view)
    private val profileShowAddressStateNameTextView =
        view.findViewById<TextView>(R.id.profile_show_address_state_name_text_view)
    private val profileShowAddressPinCodeNumberTextView =
        view.findViewById<TextView>(R.id.profile_show_address_pin_code_number_text_view)


    fun bind(addressResponseModel: AddressResponseModel) {
        profileShowAddressMobileNumberTextView.text = addressResponseModel.mobile
        profileShowAddressFlatNumberTextView.text = addressResponseModel.flat
        profileShowAddressStreetNameTextView.text = addressResponseModel.street
        profileShowAddressCityNameTextView.text = addressResponseModel.city
        profileShowAddressStateNameTextView.text = addressResponseModel.state
        profileShowAddressPinCodeNumberTextView.text = addressResponseModel.pinCode
    }
}
