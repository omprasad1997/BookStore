package com.bridgelabz.UI.address.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.responsemodel.AddressResponseModel
import com.bridgelabz.bookstore.R

class AddressAdapter(
    private val userAddressList: ArrayList<AddressResponseModel>,
    private val pickAddressHandler: () -> Unit
) :
    RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.address_item_layout, parent, false), pickAddressHandler
        )
    }

    override fun onBindViewHolder(holderBook: AddressViewHolder, position: Int) {
        holderBook.bind(userAddressList[position])
    }

    override fun getItemCount(): Int {
        return userAddressList.size
    }
}
