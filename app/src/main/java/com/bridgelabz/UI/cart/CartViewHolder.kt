package com.bridgelabz.UI.cart

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var TAG = "BookViewHolder"
    private val cartBookName = view.findViewById<TextView>(R.id.cartBookName)
    private val cartBookAuthor = view.findViewById<TextView>(R.id.cartBookAuthor)
    private val cartBookPrice = view.findViewById<TextView>(R.id.cartBookPrice)
    private val cartBookImage = view.findViewById<ImageView>(R.id.cartBookImage)
//    private val cartProceddToBuyButton = view.findViewById<Button>(R.id.proceedToBuyButton)
//    private val cartIncrementButton = view.findViewById<Button>(R.id.cartIncrementButton)
//    private val cartDecrementButton = view.findViewById<Button>(R.id.cartDecrementButton)


    init{
        Toast.makeText(itemView.context, "cart item clicked", Toast.LENGTH_SHORT).show()
    }

    fun bind(bookModel: BookModel) {
        cartBookName.text = bookModel.bookName
        cartBookAuthor.text = bookModel.bookAuthor
        cartBookPrice.text = bookModel.originalPrice
        Glide.with(itemView.context)
            .load(
                itemView.context.resources.getIdentifier(
                    bookModel.bookImage,
                    "drawable",
                    itemView.context.packageName
                )
            )
            .into(cartBookImage)
    }
}