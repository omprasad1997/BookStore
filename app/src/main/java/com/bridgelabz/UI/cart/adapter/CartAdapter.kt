package com.bridgelabz.UI.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.cart.CartViewHolder
import com.bridgelabz.UI.model.CartModel
import com.bridgelabz.bookstore.R

class CartAdapter(
    private val cartBookItem: ArrayList<CartModel>,
    private val cartItemDecrementHandler: (position: Int, bookId:Int) -> Unit,
    private val cartItemIncrementHandler: (bookId:Int) -> Unit
) :
    RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_layout_item, parent, false), cartItemDecrementHandler, cartItemIncrementHandler
        )
    }

    override fun onBindViewHolder(holderBook: CartViewHolder, position: Int) {
        holderBook.bind(cartBookItem[position])
    }

    override fun getItemCount(): Int {
        return cartBookItem.size
    }

    fun remove(position: Int) {
        cartBookItem.removeAt(position)
        notifyItemRemoved(position)
    }
}