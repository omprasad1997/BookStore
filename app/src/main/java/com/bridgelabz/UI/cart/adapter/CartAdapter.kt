package com.bridgelabz.UI.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.cart.CartViewHolder
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R

class CartAdapter(private val cartBookItem: ArrayList<BookModel>) :
RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holderBook: CartViewHolder, position: Int) {
        holderBook.bind(cartBookItem[position])
    }

    override fun getItemCount(): Int {
        return cartBookItem.size
    }
}