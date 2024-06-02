package com.bridgelabz.UI.cart

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.CartModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide

class CartViewHolder(
    view: View,
    private val cartItemDecrementHandler: (position: Int, bookId: Int) -> Unit,
    private val cartItemIncrementHandler: (bookId: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private var TAG = "CartViewHolder"
    private val cartBookName = view.findViewById<TextView>(R.id.cartBookName)
    private val cartBookAuthor = view.findViewById<TextView>(R.id.cartBookAuthor)
    private val cartBookPrice = view.findViewById<TextView>(R.id.cartBookPrice)
    private val cartBookImage = view.findViewById<ImageView>(R.id.cartBookImage)

    private val cartIncrementButton = view.findViewById<ImageView>(R.id.cartIncrementButton)
    private val cartDecrementButton = view.findViewById<ImageView>(R.id.cartDecrementButton)
    private val cartBookCount = view.findViewById<TextView>(R.id.cartBookCount)


    fun bind(cartModel: CartModel) {
        var itemCount = cartModel.bookQuantity
        cartBookName.text = cartModel.book.bookName
        cartBookAuthor.text = cartModel.book.bookAuthor
        cartBookPrice.text = cartModel.book.discountedPrice
        cartBookCount.text = itemCount.toString()
        cartBookPrice.text = (cartModel.book.discountedPrice!!.toDouble() * itemCount).toString()
        Glide.with(itemView.context)
            .load(
                itemView.context.resources.getIdentifier(
                    cartModel.book.bookImage,
                    "drawable",
                    itemView.context.packageName
                )
            )
            .into(cartBookImage)

        var totalPrice = cartModel.book.discountedPrice!!.toDouble() * itemCount

        cartIncrementButton.setOnClickListener {
            itemCount++
//            cartModel.bookQuantity = itemCount
            totalPrice += cartModel.book.discountedPrice!!.toDouble()
            cartBookCount.text = itemCount.toString()
            cartBookPrice.text = totalPrice.toString()
            Log.e(TAG, "bind:cartIncrement TotalPrice: $totalPrice")
            cartItemIncrementHandler(cartModel.book.bookId)
        }

        cartDecrementButton.setOnClickListener {
            itemCount--
//            cartModel.bookQuantity = itemCount
            cartBookCount.text = itemCount.toString()
            Log.e(TAG, "bind:cartDecrement TotalPrice: $totalPrice")
            totalPrice -= cartModel.book.discountedPrice!!.toDouble()
            cartBookPrice.text = totalPrice.toString()
            cartItemDecrementHandler(adapterPosition, cartModel.book.bookId)
        }
    }
}
