package com.bridgelabz.UI.bookList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide

class BookDescriptionFragment(private val it: BookModel) : Fragment() {
    private lateinit var bookImage:ImageView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthorName:TextView
    private lateinit var priceOfBook:TextView
    private lateinit var addToCartButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_book_discription, container, false)

        initViews(view)
        return view
    }

    private fun initViews(view: View?) {

        if (view != null) {
            bookTitle = view.findViewById(R.id.bookTitle)
            bookImage = view.findViewById(R.id.imageOfBook)
            bookAuthorName = view.findViewById(R.id.bookAuthorName)
            priceOfBook =  view.findViewById(R.id.priceOfBook)
            addToCartButton = view.findViewById(R.id.addToCartButton)
        }

        setContentBook()
    }

    private fun setContentBook() {
        bookTitle.text = it.bookName
        bookAuthorName.text = it.bookAuthor
        priceOfBook.text = it.originalPrice
        Glide.with(requireContext())
            .load(
                requireContext().resources.getIdentifier(
                    it.bookImage,
                    "drawable",
                    requireContext().packageName
                )
            )
            .into(bookImage)
    }
}