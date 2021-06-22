package com.bridgelabz.UI.address.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.responsemodel.AddressResponseModel
import com.bridgelabz.bookstore.R

class ShowAddressAdapter(
    private val userAddressList: ArrayList<AddressResponseModel>?
) :
    RecyclerView.Adapter<ShowAddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAddressViewHolder {
        return ShowAddressViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_show_address_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holderBook: ShowAddressViewHolder, position: Int) {
        userAddressList?.get(position)?.let { holderBook.bind(it) }
    }

    override fun getItemCount(): Int {
        return userAddressList!!.size
    }
}