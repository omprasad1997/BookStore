package com.bridgelabz.UI.bookList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R

class BookDescriptionFragment(val it: BookModel) : Fragment() {
    private lateinit var bookTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_book_discription, container, false)

        bookTitle = view.findViewById(R.id.bookTitle)
        bookTitle.text = it.bookAuthor
        return view
    }
}