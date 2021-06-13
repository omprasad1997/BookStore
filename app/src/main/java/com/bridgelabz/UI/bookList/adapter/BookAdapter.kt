package com.bridgelabz.UI.bookList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R

class BookAdapter(
    private val bookResponseItems: List<BookModel>,
    private val handler: (bookResponse: BookModel) -> Unit
) :
    RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item_layout, parent, false), handler
        )
    }

    override fun onBindViewHolder(holderBook: BookViewHolder, position: Int) {
        holderBook.bind(bookResponseItems[position])
    }

    override fun getItemCount(): Int {
        return bookResponseItems.size
    }
}
