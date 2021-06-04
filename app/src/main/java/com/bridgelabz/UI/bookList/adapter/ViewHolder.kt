package com.bridgelabz.UI.bookList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide

class ViewHolder(view: View, handler: (book: BookModel) -> Unit) : RecyclerView.ViewHolder(view) {

    private lateinit var bookModel: BookModel
    private val bookNameTitle = view.findViewById<TextView>(R.id.bookName)
    private val bookAuthor = view.findViewById<TextView>(R.id.bookAuthor)
    private val bookImage = view.findViewById<ImageView>(R.id.bookImage)
    private val ratingOfBook = view.findViewById<TextView>(R.id.ratingOfBook)
    private val reviewCount = view.findViewById<TextView>(R.id.reviewCount)
    private val discountedPriceOfBook = view.findViewById<TextView>(R.id.discountedPriceOfBook)
    private val originalPriceOfBook = view.findViewById<TextView>(R.id.originalPriceOfBook)
    private val discountInPercentage = view.findViewById<TextView>(R.id.discountPriceInPercentage)

    init {
        itemView.setOnClickListener {
            if (::bookModel.isInitialized) handler(bookModel)
        }
    }

    fun bind(bookModel: BookModel) {
        this.bookModel = bookModel
        bookNameTitle.text = bookModel.bookName
        bookAuthor.text = bookModel.bookAuthor
        ratingOfBook.text = bookModel.rating
        reviewCount.text = bookModel.review
        discountedPriceOfBook.text = bookModel.discountedPrice
        originalPriceOfBook.text = bookModel.originalPrice
        discountInPercentage.text = bookModel.discountInPercentage
        Glide.with(itemView.context)
            .load(
                itemView.context.resources.getIdentifier(
                    bookModel.bookImage,
                    "drawable",
                    itemView.context.packageName
                )
            )
            .into(bookImage)
    }
}
